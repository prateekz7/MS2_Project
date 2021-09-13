package com.pct.reciever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.pct.reciever", "com.pct.device.command" })
public class DeviceRecieverServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceRecieverServiceApplication.class, args);
	}

}
