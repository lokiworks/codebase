package com.loki.dddplus;

import com.loki.dddplus.annotation.Step;
import com.loki.dddplus.utils.BootstrapException;
import com.loki.dddplus.utils.InternalAopUtils;
import com.loki.dddplus.utils.StringUtils;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
public class StepDef implements IRegistryAware {
    @Getter
    private String activity;
    @Getter
    private String code;
    @Getter
    private String name;
    @Getter
    private String[] tags;
    @Getter
    private IDomainStep stepBean;


    @Override
    public void registerBean(@NotNull Object bean) {
        Step step = InternalAopUtils.getAnnotation(bean, Step.class);
        this.name = step.name();
        this.tags = step.tags();
        if (!(bean instanceof  IDomainStep)){
            throw BootstrapException.ofMessage(bean.getClass().getCanonicalName(), " MUST implement IDomainStep");
        }

        this.stepBean = (IDomainStep)bean;
        this.activity = this.stepBean.activityCode();
        this.code = this.stepBean.stepCode();
        if (StringUtils.isEmpty(this.activity)){
            throw BootstrapException.ofMessage(bean.getClass().getCanonicalName(), " activityCode cannot be empty");
        }

        if (StringUtils.isEmpty(this.code)){
            throw BootstrapException.ofMessage(bean.getClass().getCanonicalName(), " stepCode cannot be empty");
        }
        InternalIndexer.index(this);
    }
}
