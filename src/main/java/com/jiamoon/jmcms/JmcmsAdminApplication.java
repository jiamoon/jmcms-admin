package com.jiamoon.jmcms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@MapperScan({"com.jiamoon.jmcms.modules", "com.jiamoon.jmcms.common.util", "com.jiamoon.jmcms.common.service"})
@SpringBootApplication
public class JmcmsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmcmsAdminApplication.class, args);
    }
}
