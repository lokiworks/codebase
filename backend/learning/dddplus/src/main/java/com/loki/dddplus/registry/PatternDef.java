package com.loki.dddplus.registry;

import com.loki.dddplus.*;
import com.loki.dddplus.annotation.Pattern;
import com.loki.dddplus.utils.BootstrapException;
import com.loki.dddplus.utils.InternalAopUtils;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ToString
public class PatternDef implements IRegistryAware, IIdentifyResolver {
    @Getter
    private String code;
    @Getter
    private String name;
    @Getter
    private int priority;

    private IIdentifyResolver patternBean;

    private Map<Class<? extends IDomainExtension>, ExtensionDef> extensionDefMap = new HashMap<>();


    @Override
    public boolean match(IDomainModel model) {
        return patternBean.match(model);
    }

    @Override
    public void registerBean(@NotNull Object bean) {
        initialize(bean);
        InternalIndexer.index(this);
    }

    private void initialize(Object bean) {
        Pattern pattern = InternalAopUtils.getAnnotation(bean, Pattern.class);
        this.code = pattern.code();
        this.name = pattern.name();
        this.priority = pattern.priority();
        if (this.priority < 0) {
            throw BootstrapException.ofMessage("Pattern.priority must be zero or positive");
        }

        if (!(bean instanceof IIdentifyResolver)) {
            throw BootstrapException.ofMessage(bean.getClass().getCanonicalName(), " MUST implements IIdentifyResolver");
        }
        this.patternBean = (IIdentifyResolver) bean;
    }

    void registerExtensionDef(ExtensionDef extensionDef) {
        Class<? extends IDomainExtension> extClazz = extensionDef.getExtClazz();
        if (extensionDefMap.containsKey(extClazz)) {
            throw BootstrapException.ofMessage("Pattern(code=", code, ") can hold ONLY one instance on ", extClazz.getCanonicalName(), ", existing ", extensionDefMap.get(extClazz).toString(), ", illegal ", extensionDef.toString());
        }
        extensionDefMap.put(extClazz, extensionDef);
    }

    ExtensionDef getExtension(Class<? extends IDomainExtension> extClazz) {
        return extensionDefMap.get(extClazz);
    }

    Set<Class<? extends IDomainExtension>> extClazzSet() {
        return extensionDefMap.keySet();
    }
}
