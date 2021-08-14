package com.loki.dddplus;

import com.loki.dddplus.utils.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

public class DDD {

    private DDD(){}

    public static <T extends BaseDomainAbility> T findAbility(@NotNull Class<? extends T> domainAbilityClazz ){
        return InternalIndexer.findDomainAbility(domainAbilityClazz);
    }

    public static <Step extends IDomainStep> List<Step> findSteps(@NotNull String activityCode, @NotNull List<String> stepCodeList){
        // TODO:
        return null;
    }

    public static <Ext extends IDomainExtension, R> Ext firstExtension(@NotNull Class<Ext> extClazz, IDomainModel model){
        ExtensionInvocationHandler<Ext, R> proxy = new ExtensionInvocationHandler<>(extClazz, model, null, null, 0);
        return proxy.createProxy();
    }

    public static <Step extends IDomainStep> Step getStep(@NotNull String activityCode, @NotNull String stepCode){
        List<Step> steps = findSteps(activityCode, Arrays.asList(stepCode));
        if (CollectionUtils.isEmpty(steps)){
            return null;
        }
        return steps.get(0);
    }

}
