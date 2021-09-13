package com.pct.device.simulator;

import org.springframework.beans.factory.annotation.Autowired;

import com.pct.device.simulator.util.DeviceSimulator;

public class BeanInitMethodImpl {
	@Autowired
	DeviceSimulator deviceSimulator;

	public void runAfterObjectCreated() {

		Thread t1 = new Thread(deviceSimulator);
		t1.start();
	}
}