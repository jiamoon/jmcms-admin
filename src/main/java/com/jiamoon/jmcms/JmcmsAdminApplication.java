package com.jiamoon.jmcms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class JmcmsAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(JmcmsAdminApplication.class, args);
	}
}
