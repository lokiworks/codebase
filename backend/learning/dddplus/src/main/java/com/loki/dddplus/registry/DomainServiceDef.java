package com.loki.dddplus.registry;

import com.loki.dddplus.IDomainService;
import com.loki.dddplus.IRegistryAware;
import com.loki.dddplus.InternalIndexer;
import com.loki.dddplus.annotation.DomainService;
import com.loki.dddplus.utils.BootstrapException;
import com.loki.dddplus.utils.InternalAopUtils;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
public class DomainServiceDef implements IRegistryAware {
    @Getter
    private String domain;
    @Getter
    private IDomainService domainService;
    @Override
    public void registerBean(@NotNull Object bean) {
        DomainService domainService = InternalAopUtils.getAnnotation(bean, DomainService.class);
        if (!(bean instanceof IDomainService)){
            throw BootstrapException.ofMessage(bean.getClass().getCanonicalName(), "MUST implement IDomainService");
        }
        this.domain = domainService.domain();
        this.domainService = (IDomainService)bean;
        InternalIndexer.index(this);

    }
}
