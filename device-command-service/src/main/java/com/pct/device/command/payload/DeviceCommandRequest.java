package com.pct.device.command.payload;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Instant;

import javax.persistence.Column;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Setter
@Getter
public class DeviceCommandRequest {

	public DeviceCommandRequest(String device_id, String at_command, String source, byte[] packet,
			InetAddress inetAddress, int i) {
		super();
		this.device_id = device_id;
		this.at_command = at_command;
		this.source = source;
		this.packet = packet;
		this.listenerIPAddress = inetAddress;
		this.listenerPort = i;
	}

	private String device_id;

	private String at_command;

	private String source;

	private String priority;

	private Instant last_executed;

	private boolean success;

	private byte[] packet;

	private int listenerPort;
	private InetAddress listenerIPAddress;
	private InetAddress deviceIPAddress;
	private int devicePort;
	
}
