package com.pct.reciever.models;

import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.time.Instant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReceivedUDPPacket {

	private Timestamp timeStamp;
	private byte[] packet;
	private InetAddress deviceIPAddress;
	private int devicePort;
	private int listenerPort;

	private String customerName;
	private String serviceNetwork;

	private String uuid;
	private Instant receivedTime;
	private InetAddress listenerIPAddress;

	public ReceivedUDPPacket(byte[] packet, InetAddress clientIPAddress, int clientPort, int serverPort, String uuid,
			Instant receivedTime) {

		Instant now = Instant.now();
		this.timeStamp = Timestamp.from(now);

		this.packet = packet;
		this.deviceIPAddress = clientIPAddress;
		this.devicePort = clientPort;
		this.listenerPort = serverPort;
		this.uuid = uuid;
		this.receivedTime = receivedTime;
	}

	public ReceivedUDPPacket(byte[] receivedDataPacket, InetAddress deviceIPAddress, int devicePort,
			Inet4Address listenerIP, int listenerPort, String uuid, Instant instant) {
		Instant now = Instant.now();
		this.timeStamp = Timestamp.from(now);

		this.packet = receivedDataPacket;
		this.deviceIPAddress = deviceIPAddress;
		this.devicePort = devicePort;
		this.listenerIPAddress = listenerIP;
		this.listenerPort = listenerPort;
		this.uuid = uuid;
		this.receivedTime = instant;
	}

}
