package com.pct.reciever.engine;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SocketObject {


	private static DatagramSocket socket = null;
	private static InetAddress clientIPAddress;

	public static DatagramSocket getSocket(String serverIP, int serverPort) throws Exception {
		if (socket == null) {

			socket = new DatagramSocket(serverPort, InetAddress.getByName(serverIP));

		}
		return socket;
	}
}
