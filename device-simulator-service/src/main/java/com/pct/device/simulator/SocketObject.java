package com.pct.device.simulator;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SocketObject {

	static String serverIP = "127.0.0.1";
	static int serverPort = 15022;
	private static DatagramSocket socket = null;
	private static InetAddress clientIPAddress;

	public static DatagramSocket getSocket() throws Exception {
		if (socket == null) {

			socket = new DatagramSocket(serverPort, InetAddress.getByName(serverIP));

		}
		return socket;
	}
}
