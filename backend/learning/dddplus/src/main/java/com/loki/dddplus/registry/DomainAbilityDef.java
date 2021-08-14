package com.loki.dddplus.registry;

import com.loki.dddplus.BaseDomainAbility;
import com.loki.dddplus.IDomainExtension;
import com.loki.dddplus.IRegistryAware;
import com.loki.dddplus.InternalIndexer;
import com.loki.dddplus.annotation.DomainAbility;
import com.loki.dddplus.utils.BootstrapException;
import com.loki.dddplus.utils.InternalAopUtils;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;

import javax.validation.constraints.NotNull;

@ToString
@Slf4j
public class DomainAbilityDef implements IRegistryAware {
    @Getter
    private String domain;
    @Getter
    private String name;
    @Getter
    private BaseDomainAbility domainAbilityBean;
    private Class<? extends BaseDomainAbility> domainAbilityClass;
    private Class<? extends IDomainExtension> extClazz;



    @Override
    public void registerBean(@NotNull Object bean) {
        DomainAbility domainAbility  = InternalAopUtils.getAnnotation(bean, DomainAbility.class);
        this.domain = domainAbility.domain();
        this.name = domainAbility.name();
        if (!(bean instanceof BaseDomainAbility)){
            throw BootstrapException.ofMessage(bean.getClass().getCanonicalName(), " Must extends BaseDomainAbility");

        }

        this.domainAbilityBean = (BaseDomainAbility)bean;
        this.domainAbilityClass = ( Class<? extends BaseDomainAbility>)InternalAopUtils.getTarget(bean).getClass();
        this.resolveExtClazz();
        log.debug("domain ability:{} ext:{}", bean.getClass().getCanonicalName(), extClazz.getCanonicalName());
        InternalIndexer.index(this);

    }

    private void resolveExtClazz(){
        ResolvableType baseDomainAbilityType = ResolvableType.forType(this.domainAbilityClass).getSuperType();
        for (int i = 0; i < 5; i++){
            for (ResolvableType resolvableType: baseDomainAbilityType.getGenerics()){
                if (IDomainExtension.class.isAssignableFrom(resolvableType.resolve())){
                    this.extClazz = (Class<? extends IDomainExtension>)resolvableType.resolve();
                    return;
                }
                baseDomainAbilityType = baseDomainAbilityType.getSuperType();
            }
        }
        throw BootstrapException.ofMessage("Even after 5 tries, still unable to figure out the extension class of BaseDomainAbility:", this.domainAbilityClass.getCanonicalName());

    }

}
