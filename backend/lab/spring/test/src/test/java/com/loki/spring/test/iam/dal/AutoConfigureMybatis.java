/**
 * Copyright 2015-2017 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.loki.spring.test.iam.dal;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * {@link ImportAutoConfiguration Auto-configuration imports} for typical Mybatis tests. Most tests should consider
 * using {@link MybatisTest @MybatisTest} rather than using this annotation directly.
 *
 * @author wonwoo
 * @since 1.2.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration
@Import({FlywayAutoConfiguration.class, DataSourceAutoConfiguration.class, org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration.class, org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration.class, org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration.class, org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration.class, com.baomidou.mybatisplus.autoconfigure.MybatisPlusLanguageDriverAutoConfiguration.class, com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration.class})
public @interface AutoConfigureMybatis {

}
