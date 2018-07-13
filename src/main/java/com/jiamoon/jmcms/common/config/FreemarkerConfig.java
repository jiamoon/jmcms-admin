package com.jiamoon.jmcms.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.Map;

@Configuration
public class FreemarkerConfig {
    /**
     * 后台根路径
     */
    @Value("${jmcms.adminPath}")
    String adminPath;
    /**
     * 产品名称
     */
    @Value("${jmcms.productName}")
    String productName;

    @Bean
    public CommandLineRunner customFreemarker(FreeMarkerViewResolver resolver) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                //添加自定义变量
                Map<String, Object> map = resolver.getAttributesMap();
                map.put("adminPath", adminPath);
                map.put("productName", productName);
            }
        };
    }
}
