package com.pct.reciever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.pct.reciever", "com.pct.device.command"})
public class MsVersion2Application {

	public static void main(String[] args) {
		SpringApplication.run(MsVersion2Application.class, args);
		System.out.println("hi");
		
	}

}
