package com.pct.device.simulator.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pct.device.simulator.SocketObject;
@Component
public class DeviceSimulator implements Runnable {

	static Logger logger = LoggerFactory.getLogger(DeviceSimulator.class);
	private boolean isRunning = true;
	public static final int LISTEN_PACKET_LEN = 2048;
	private DatagramSocket socket = null;
	private InetAddress clientIPAddress;
	@Value("${simulator.server.ip}")
	private String serverIp;
	@Value("${simulator.server.port}")
	private int serverPort;

	@Override
	public void run() {
		try {
			socket = SocketObject.getSocket(serverIp,serverPort);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (isRunning) {
			try {
				byte[] buffer = new byte[LISTEN_PACKET_LEN];
				logger.info("Inside thread");
				// receive UDP packet
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				logger.info("Ackknowledgment packet recieve");
			} catch (IOException e) {
				logger.info("Exception " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
