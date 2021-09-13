package com.pct.device.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = { "com.pct.device.command" })
@ComponentScan(basePackages = { "com.pct.device.command" })
@EnableJpaRepositories(basePackages = { "com.pct.device.command" })
@EnableTransactionManagement
public class DeviceCommandServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceCommandServiceApplication.class, args);
	}

}
