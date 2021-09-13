package com.pct.device.command.service;

import com.pct.device.command.entity.DeviceCommandManager;
import com.pct.device.command.payload.DeviceCommandRequest;

public interface IDeviceCommandService {

	public DeviceCommandManager saveDeviceCommand(DeviceCommandRequest deviceCommandRequest);

	public DeviceCommandManager getDeviceCommandFromRedis(String deviceID);

}
