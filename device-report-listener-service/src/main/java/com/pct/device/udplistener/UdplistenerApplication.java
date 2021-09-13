package com.pct.device.udplistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pct.device.udplistener.util.UDPListener;

@SpringBootApplication
public class UdplistenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UdplistenerApplication.class, args);
		UDPListener r1 = new UDPListener();
		Thread t1 = new Thread(r1);
		t1.start();
	}

}
