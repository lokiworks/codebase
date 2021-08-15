package com.loki.dddplus.registry;

import com.loki.dddplus.*;
import com.loki.dddplus.annotation.Partner;
import com.loki.dddplus.utils.BootstrapException;
import com.loki.dddplus.utils.InternalAopUtils;
import lombok.AccessLevel;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class PartnerDef implements IRegistryAware, IPrepareAware, IIdentifyResolver {
    @Getter
    private String code;
    @Getter
    private String name;
    @Getter
    private IIdentifyResolver partnerBean;



    @Getter(AccessLevel.PACKAGE)
    private Map<Class<? extends IDomainExtension>, ExtensionDef> extensionDefMap = new HashMap<>();


    @Override
    public boolean match(IDomainModel model) {
        return partnerBean.match(model);
    }

    @Override
    public void prepare(@NotNull Object bean) {
            initialize(bean);
            InternalIndexer.prepare(this);
    }

    @Override
    public void registerBean(@NotNull Object bean) {
        initialize(bean);
        InternalIndexer.index(this);
    }

    private void initialize(Object bean){
        Partner partner = InternalAopUtils.getAnnotation(bean, Partner.class);
        this.code = partner.code();
        this.name = partner.name();

        if (!(bean instanceof IIdentifyResolver)){
            throw BootstrapException.ofMessage(bean.getClass().getCanonicalName(), "MUST implements IIdentityResolver");
        }
        this.partnerBean = (IIdentifyResolver)bean;
    }

    void registerExtensionDef(ExtensionDef extensionDef){
        Class<? extends IDomainExtension> extClazz = extensionDef.getExtClazz();
        if (extensionDefMap.containsKey(extClazz)){
            throw BootstrapException.ofMessage("Partner(code=", code, ") can hold ONLY one instance on", extClazz.getCanonicalName(), ", existing ", extensionDefMap.get(extClazz).toString(), ", illegal ", extensionDef.toString());
        }
        extensionDefMap.put(extClazz, extensionDef);
    }

    ExtensionDef getExtension(Class<? extends IDomainExtension> extClazz){
        return extensionDefMap.get(extClazz);
    }

}
