package com.loki.dddplus.registry;

import com.loki.dddplus.*;
import com.loki.dddplus.annotation.*;
import com.loki.dddplus.utils.BootstrapException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Supplier;

@Component
@Slf4j
public class RegistryFactory implements InitializingBean {
    static List<RegistryEntry> validRegistryEntries = new ArrayList<>();
    private static Map<Class<? extends Annotation>, PrepareEntry> validPrepareEntries = new HashMap<>(3);

    void register(ApplicationContext applicationContext){
        for (RegistryEntry entry: validRegistryEntries){
            log.info("register {}'s ...", entry.annotation.getCanonicalName());
            for (Object bean: applicationContext.getBeansWithAnnotation(entry.annotation).values()){
                entry.create().registerBean(bean);
            }
        }

        InternalIndexer.postIndexing();
    }

    static void preparePlugins(Class<? extends Annotation> annotation, Object bean){
        if (!(bean instanceof IPlugable)){
            throw BootstrapException.ofMessage(bean.getClass().getCanonicalName() + " must be IPlugable");
        }
        PrepareEntry prepareEntry = validPrepareEntries.get(annotation);
        if (Objects.isNull(prepareEntry)){
            throw BootstrapException.ofMessage(annotation.getCanonicalName(), " not supported");
        }

        prepareEntry.create().prepare(bean);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("setup the discoverable Spring beans...");
        // 注册domain
        validRegistryEntries.add(new RegistryEntry(Domain.class, ()->new DomainDef()));
        validRegistryEntries.add(new RegistryEntry(DomainService.class, ()->new DomainServiceDef()));
        validRegistryEntries.add(new RegistryEntry(Specification.class, ()->new SpecificationDef()));
        validRegistryEntries.add(new RegistryEntry(Step.class, ()->new StepDef()));
        validRegistryEntries.add(new RegistryEntry(DomainAbility.class, ()->new DomainAbilityDef()));
        validRegistryEntries.add(new RegistryEntry(Policy.class, ()->new PolicyDef()));
        validRegistryEntries.add(new RegistryEntry(Partner.class, ()->new PartnerDef()));
        validRegistryEntries.add(new RegistryEntry(Pattern.class, ()->new PartnerDef()));
        validRegistryEntries.add(new RegistryEntry(Extension.class, ()->new ExtensionDef()));


        validPrepareEntries.put(Partner.class, new PrepareEntry(()->new PartnerDef()));
        validPrepareEntries.put(Extension.class, new PrepareEntry(()->new ExtensionDef()));

    }

    private static class RegistryEntry{
        private final Class<? extends Annotation> annotation;
        private final Supplier<IRegistryAware> supplier;

        RegistryEntry(Class<? extends Annotation> annotation, Supplier<IRegistryAware> supplier){
            this.annotation = annotation;
            this.supplier = supplier;
        }

        IRegistryAware create(){
            return supplier.get();
        }

    }

    private static class PrepareEntry{
        private final Supplier<IPrepareAware> supplier;
        PrepareEntry(Supplier<IPrepareAware> supplier){
            this.supplier = supplier;
        }
        IPrepareAware create(){
            return supplier.get();
        }
    }
}
