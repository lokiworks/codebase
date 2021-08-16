package com.loki.dddplus.registry;

import com.loki.dddplus.IRegistryAware;
import com.loki.dddplus.ISpecification;
import com.loki.dddplus.InternalIndexer;
import com.loki.dddplus.annotation.Specification;
import com.loki.dddplus.utils.BootstrapException;
import com.loki.dddplus.utils.InternalAopUtils;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
public class SpecificationDef implements IRegistryAware {
    @Getter
    private String name;
    @Getter
    private String[] tags;

    private ISpecification specification;

    @Override
    public void registerBean(@NotNull Object bean) {
        Specification specification = InternalAopUtils.getAnnotation(bean, Specification.class);
        this.name = specification.value();
        this.tags = specification.tags();
        if (!(bean instanceof  ISpecification)){
            throw BootstrapException.ofMessage(bean.getClass().getCanonicalName(), " MUST implement ISpecification");
        }
        this.specification = (ISpecification)bean;
        InternalIndexer.index(this);
    }
}
