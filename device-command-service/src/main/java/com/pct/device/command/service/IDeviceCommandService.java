package com.pct.device.command.service;

import java.net.InetAddress;

import com.pct.device.command.entity.DeviceCommand;
import com.pct.device.command.payload.DeviceCommandRequest;

public interface IDeviceCommandService {

	public DeviceCommand saveDeviceCommand(DeviceCommandRequest deviceCommandRequest);

	public DeviceCommand getDeviceCommandFromRedis(String deviceID);

	void processAck(byte[] packet, InetAddress deviceInetAddress, String deviceId, int devicePort,
			InetAddress listenerInetAddress, int listenerPort);

	void processATCommand(String deviceId, InetAddress deviceInetAddress, int devicePort,
			InetAddress listenerInetAddress, int listenerPort);

}
