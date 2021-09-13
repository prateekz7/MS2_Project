package com.pct.device.udplistener;

import org.springframework.beans.factory.annotation.Autowired;

import com.pct.device.udplistener.util.UDPListener;

public class BeanInitMethodImpl {
 @Autowired
 	UDPListener uDPListener;
    public void runAfterObjectCreated() {
    
    	Thread t1 = new Thread(uDPListener);
		t1.start();
    }
} 