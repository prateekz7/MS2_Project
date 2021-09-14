package com.pct.device.simulator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

import com.pct.device.simulator.util.DeviceSimulator;

public class BeanInitMethodImpl {
	@Autowired
	DeviceSimulator deviceSimulator;

	public void runAfterObjectCreated() {
		ExecutorService executorService = Executors.newCachedThreadPool();

		executorService.execute(new Runnable() {
		    public void run() {
		    	deviceSimulator.run();
		        System.out.println("Asynchronous task");
		    }
		});

		executorService.shutdown();
		//Thread t1 = new Thread(deviceSimulator);
		//t1.start();
	}
}