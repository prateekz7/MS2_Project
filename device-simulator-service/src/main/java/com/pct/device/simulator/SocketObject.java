package com.pct.device.simulator;

import java.net.DatagramSocket;
import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class SocketObject {

/*	@Value("${simulator.server.ip}")
	private static String serverIP;
	@Value("${simulator.server.port}")
	private static int serverPort;*/

	private static DatagramSocket socket = null;
	private static InetAddress clientIPAddress;

	public static DatagramSocket getSocket(String serverIP , int serverPort) throws Exception {
		
		System.out.println("Starting server on IP "+serverIP +" Port : "+serverPort);
		if (socket == null) {

			socket = new DatagramSocket(serverPort, InetAddress.getByName(serverIP));

		}
		return socket;
	}
}
