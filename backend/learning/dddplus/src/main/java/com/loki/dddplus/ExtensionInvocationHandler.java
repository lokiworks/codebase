package com.loki.dddplus;

import com.loki.dddplus.registry.ExtensionDef;
import com.loki.dddplus.utils.CollectionUtils;
import com.loki.dddplus.utils.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
class ExtensionInvocationHandler<Ext extends IDomainExtension, R> implements InvocationHandler {

    private final Class<Ext> extInterface;
    private final IDomainModel model;
    private final IReducer<R> reducer;
    private final Ext defaultExt;
    private int timeoutInMs;

    private static ExecutorService extInvokeTimerExecutor = new ThreadPoolExecutor(10, Objects.nonNull(System.getProperty("invokeExtMaxPoolSize")) ? Integer.valueOf(System.getProperty("invokeExtMaxPoolSize")) : 50,
            5L,
            TimeUnit.MINUTES,
            new SynchronousQueue<>(),//  无队列，线程池满后新请求进来则 RejectedExecutionException
            new NamedThreadFactory("ExtInvokeTimer", false) // daemon=false,shutdown时等待扩展点执行完毕
    );


    ExtensionInvocationHandler(@NotNull Class<Ext> extInterface, @NotNull IDomainModel model, IReducer<R> reducer, Ext defaultExt, int timeoutInMs) {
        this.extInterface = extInterface;
        this.model = model;
        this.reducer = reducer;
        this.defaultExt = defaultExt;
        this.timeoutInMs = timeoutInMs;
    }

    Ext createProxy() {
        return (Ext) Proxy.newProxyInstance(extInterface.getClassLoader(), new Class[]{this.extInterface}, this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<ExtensionDef> effectiveExts = InternalIndexer.findEffectiveExtensions(extInterface, model, Objects.isNull(reducer));
        if (log.isDebugEnabled()) {
            log.debug("{} effective {}", extInterface.getCanonicalName(), effectiveExts);
        }

        if (CollectionUtils.isEmpty(effectiveExts)) {
            if (Objects.isNull(defaultExt)) {
                if (log.isDebugEnabled()) {
                    log.debug("found NO ext instance {} on {}, HAS TO return null", extInterface.getCanonicalName(), model);
                }
                return null;
            }
            if (log.isDebugEnabled()) {
                log.debug("use default {}", defaultExt);
                if (Objects.isNull(effectiveExts)) {
                    effectiveExts = new ArrayList<>();
                }
                effectiveExts.add(new ExtensionDef(defaultExt));
            }
        }
        // all effective extension instances found
        List<R> accumulateResults = new ArrayList<>(effectiveExts.size());
        R result = null;
        for (ExtensionDef extensionDef : effectiveExts) {
            result = invokeExtension(extensionDef, method, args);
            accumulateResults.add(result);
            if (Objects.isNull(reducer) || reducer.shouldStop(accumulateResults)) {
                break;
            }
        }

        if (Objects.nonNull(reducer)) {
            // reducer决定最终的返回值
            return this.reducer.reduce(accumulateResults);
        }


        return result;
    }

    private R invokeExtension(ExtensionDef extensionDef, final Method method, Object[] args) throws Throwable {
        try {
            return invokeExtensionMethod(extensionDef, method, args);
        } catch (InvocationTargetException e) {
            log.error("{} code:{}", this.extInterface.getCanonicalName(), extensionDef.getCode(), e.getTargetException());
            throw e.getTargetException();
        } catch (TimeoutException e) {
            log.error("time out:{}ms, {} method:{} args:{}", timeoutInMs, extensionDef.getExtensionBean(), method.getName(), args);
            throw new ExtTimeoutException(timeoutInMs);
        } catch (RejectedExecutionException e) {
            log.error("ExtInvokeTimer thread pool FULL:{}", e.getMessage()); // 需要加日志报警
            throw e;
        } catch (Throwable e) {
            // should never happen
            log.error("{} code:{} unexcepted", this.extInterface.getCanonicalName(), extensionDef.getCode(), e);
            throw e;
        }
    }

    private R invokeExtensionMethod(ExtensionDef extensionDef, final Method method, Object[] args) throws Throwable {
        IDomainExtension extInstance = extensionDef.getExtensionBean();
        if (timeoutInMs > 0) {
            return invokeExtensionMethodWithTimeout(extInstance, method, args, timeoutInMs);
        }
        R result = (R) method.invoke(extInstance, args);
        log.debug("{} method:{} args:{}, result:{}", extInstance, method.getName(), args, result);
        return result;
    }

    private R invokeExtensionMethodWithTimeout(IDomainExtension extInstance, final Method method, Object[] args, final int timeoutInMs) throws Throwable {

        Future<R> future = extInvokeTimerExecutor.submit(() -> (R) method.invoke(extInstance, args));
        try {
            R result = future.get(timeoutInMs, TimeUnit.MILLISECONDS);
            log.debug("{} method:{} args:{}, result:{}", extInstance, method.getName(), args, result);
            return result;
        } catch (TimeoutException e) {
            if (!future.isCancelled()) {
                future.cancel(true); // be effort
            }

            throw e;
        } catch (ExecutionException e) {
            // future的异常机制，这里尽可能把真实的异常抛出去
            throw Objects.nonNull(e.getCause()) ? e.getCause() : e;
        }
    }


}
