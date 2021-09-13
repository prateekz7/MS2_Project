package com.pct.device.udplistener.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pct.device.udplistener.engine.Producer;
import com.pct.tlv.common.utils.StringUtilities;
@Component
public class UDPListener implements Runnable {

	static Logger logger = LoggerFactory.getLogger(UDPListener.class);
	private boolean isRunning = true;

	@Value("${listener.udp.ip}")
	private String serverIP;
	@Value("${listener.udp.port}")
	private int serverPort;

	private DatagramSocket socket = null;

	Producer producer = new Producer();

	@Override
	public void run() {
		System.out.println("Starting UDP Listener on " +serverIP +" : " +serverPort);
		try {
			socket = new DatagramSocket(serverPort, InetAddress.getByName(serverIP));
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (isRunning) {
			try {
				byte[] buffer = new byte[StringUtilities.LISTEN_PACKET_LEN];
				logger.info("Inside thread");
				// receive UDP packet
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				logger.info("data packet recieved at UDP listener from device");
				String rawString = StringUtilities.Byte2HexString(packet.getData(), 0, 256);
				InetAddress inetAddress = InetAddress.getByName(serverIP);
				byte[] buf = StringUtilities.Hex2Byte(rawString);
			//	DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, inetAddress, serverPort);
				producer.sendMessage(packet, StringUtilities.TOPIC);
				logger.info("data packet sent using kafka");
			} catch (IOException e) {
				logger.info("Exception " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
