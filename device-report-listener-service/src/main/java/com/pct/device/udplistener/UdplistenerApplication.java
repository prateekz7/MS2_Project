package com.pct.device.udplistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.pct.device.udplistener.util.UDPListener;

@SpringBootApplication
public class UdplistenerApplication {

	@Autowired
	UDPListener uDPListener;
	public static void main(String[] args) {
		SpringApplication.run(UdplistenerApplication.class, args);
	
	
	}
	 @Bean(initMethod="runAfterObjectCreated")
	    public BeanInitMethodImpl getFunnyBean() {
	        return new BeanInitMethodImpl();
	    }
}
