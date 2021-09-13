package com.pct.device.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pct.device.simulator.util.DeviceSimulator;

@SpringBootApplication
public class DeviceSimulatorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceSimulatorServiceApplication.class, args);
		DeviceSimulator r1 = new DeviceSimulator();
		Thread t1 = new Thread(r1);
		t1.start();
	}

}
