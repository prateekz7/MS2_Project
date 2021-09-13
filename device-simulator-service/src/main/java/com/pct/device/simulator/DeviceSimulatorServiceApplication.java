package com.pct.device.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.pct.device.simulator.util.DeviceSimulator;

@SpringBootApplication
public class DeviceSimulatorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceSimulatorServiceApplication.class, args);

	}

	@Bean(initMethod = "runAfterObjectCreated")
	public BeanInitMethodImpl getThreadBean() {
		return new BeanInitMethodImpl();
	}

}
