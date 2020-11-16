package com.loki.spring.test.iam.dal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = "com.loki.spring.test.iam.dal", lazyInitialization = "true")
public class TestDalApplication {
}
