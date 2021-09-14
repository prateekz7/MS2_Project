package com.pct.device.udplistener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

import com.pct.device.udplistener.util.UDPListener;

public class BeanInitMethodImpl {
	@Autowired
	UDPListener uDPListener;

	public void runAfterObjectCreated() {
		ExecutorService executorService = Executors.newCachedThreadPool();

		executorService.execute(new Runnable() {
			public void run() {
				uDPListener.run();
				System.out.println("Asynchronous task");
			}
		});
		executorService.shutdown();
	}

}