package com.pct.reciever.utils;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;

public class GeneralUtilities {
	public static InetAddress GetOutboundAddress(SocketAddress remoteAddress) throws SocketException {
		DatagramSocket sock = new DatagramSocket();
		// connect is needed to bind the socket and retrieve the local address
		// later (it would return 0.0.0.0 otherwise)
		sock.connect(remoteAddress);

		final InetAddress localAddress = sock.getLocalAddress();

		sock.disconnect();
		sock.close();
		sock = null;

		return localAddress;
	}
}
