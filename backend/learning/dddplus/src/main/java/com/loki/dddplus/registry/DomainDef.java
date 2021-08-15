package com.loki.dddplus.registry;

import com.loki.dddplus.IRegistryAware;
import com.loki.dddplus.InternalIndexer;
import com.loki.dddplus.annotation.Domain;
import com.loki.dddplus.utils.InternalAopUtils;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
public class DomainDef implements IRegistryAware {
    @Getter
    private String code;
    @Getter
    private String name;
    @Getter
    private Object domainObject;
    @Override
    public void registerBean(@NotNull Object bean) {
        Domain domain  = InternalAopUtils.getAnnotation(bean, Domain.class);
        this.code = domain.code();
        this.name = domain.name();
        this.domainObject = bean;
        InternalIndexer.index(this);
    }
}
