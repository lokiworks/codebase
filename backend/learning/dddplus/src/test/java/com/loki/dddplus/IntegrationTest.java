package com.loki.dddplus;

import com.loki.dddplus.ability.FooDomainAbility;
import com.loki.dddplus.registry.DomainAbilityDef;
import com.loki.dddplus.utils.BootstrapException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-test.xml"})
@Slf4j
public class IntegrationTest {
@Test
    public void findDomainAbility(){
        FooDomainAbility fooDomainAbility = new FooDomainAbility();
        DomainAbilityDef domainAbilityDef = new DomainAbilityDef();
        try {
            domainAbilityDef.registerBean(fooDomainAbility);
        }catch (BootstrapException expected){

        }
    }

}
