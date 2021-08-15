package com.loki.dddplus.registry;

import com.loki.dddplus.*;
import com.loki.dddplus.annotation.Policy;
import com.loki.dddplus.utils.BootstrapException;
import com.loki.dddplus.utils.InternalAopUtils;
import com.loki.dddplus.utils.StringUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@ToString
public class PolicyDef implements IRegistryAware {

    private IExtPolicy policyBean;
    private Class<? extends IDomainExtension> extClazz;
    private Map<String, ExtensionDef> extensionDefMap = new HashMap<>();

    @Override
    public void registerBean(@NotNull Object bean) {
        initialize(bean);
        InternalIndexer.index(this);
    }

    private void initialize(Object bean){
        Policy policy = InternalAopUtils.getAnnotation(bean, Policy.class);
        this.extClazz = policy.extClazz();
        if (!(bean instanceof IExtPolicy)){
            throw BootstrapException.ofMessage(bean.getClass().getCanonicalName(), " MUST implements IExtPolicy");
        }
        this.policyBean = (IExtPolicy)bean;
    }

    void registerExtensionDef(ExtensionDef extensionDef){
        extensionDefMap.put(extensionDef.getCode(), extensionDef);
    }

    ExtensionDef getExtension(IDomainModel model){
        final String extensionCode = policyBean.extensionCode(model);
        if (StringUtils.isEmpty(extensionCode)){
            return null;
        }
        return extensionDefMap.get(extensionCode);
    }

    String policyName(){
        return policyBean.getClass().getCanonicalName();
    }
}
