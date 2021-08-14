package com.loki.dddplus;

import com.loki.dddplus.annotation.Partner;
import com.loki.dddplus.registry.*;

import javax.validation.constraints.NotNull;
import java.util.List;

public class InternalIndexer {
    private InternalIndexer(){}


    public static Class<? extends IDomainExtension> getDomainAbilityExtDeclaration(@NotNull Class<? extends BaseDomainAbility> clazz){
        // TODO: add code here.
        return null;
    }

    public static List<ExtensionDef> findEffectiveExtensions(@NotNull Class<? extends IDomainExtension> extClazz, @NotNull IDomainModel model, boolean firstStop){
        // TODO: add code here.
        return null;
    }

    public static void index(DomainDef domainDef){}

    public static void index(DomainAbilityDef domainDef){}
    public static void index(DomainServiceDef domainServiceDef){}
    public static void index(PartnerDef partnerDef){}
    public static void index(PatternDef patternDef){}
    public static void index(PolicyDef policyDef){}
    public static void index(SpecificationDef specificationDef){}
    public static void index(StepDef stepDef){}

    public static void prepare(PartnerDef partnerDef){

    }

    public static <T extends BaseDomainAbility> T findDomainAbility(@NotNull Class<? extends T> clazz){
        return null;
    }


    public static void postIndexing(){

    }


}
