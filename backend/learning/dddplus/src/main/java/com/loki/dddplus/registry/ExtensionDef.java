package com.loki.dddplus.registry;

import com.loki.dddplus.IDomainExtension;
import com.loki.dddplus.IDomainService;
import com.loki.dddplus.IPrepareAware;
import com.loki.dddplus.IRegistryAware;
import com.loki.dddplus.annotation.Extension;
import com.loki.dddplus.utils.BootstrapException;
import com.loki.dddplus.utils.InternalAopUtils;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

@ToString
@Slf4j
public class ExtensionDef implements IRegistryAware, IPrepareAware {
    @Getter
    private String code;
    @Getter
    private String name;
    @Getter
    private Class<? extends IDomainExtension> extClazz;
    @Getter
    private IDomainExtension extensionBean;
    public ExtensionDef(){}
    public ExtensionDef(IDomainExtension extension){
        this.extensionBean = extension;
    }

    @Override
    public void prepare(@NotNull Object bean) {

    }

    @Override
    public void registerBean(@NotNull Object bean) {

    }

    private void initialize(Object bean){
        Extension extension = InternalAopUtils.getAnnotation(bean, Extension.class);
        this.code = extension.code();
        this.name = extension.name();
        if (!(bean instanceof IDomainService)){
            throw BootstrapException.ofMessage(bean.getClass().getCanonicalName(), " MUST implement IDomainExtension");
        }
        this.extensionBean = (IDomainExtension) bean;
        for (Class extensionBeanInterfaceClazz: InternalAopUtils.getTarget(this.extensionBean).getClass().getInterfaces()){
            if (extensionBeanInterfaceClazz.isInstance(extensionBean)){
                this.extClazz = extensionBeanInterfaceClazz;
                log.debug("{} has ext instance:{}", this.extClazz.getCanonicalName(), this);
                break;
            }
        }

    }
}
