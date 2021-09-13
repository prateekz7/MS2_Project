package com.pct.reciever.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pct.device.command.payload.DeviceCommandRequest;
import com.pct.device.command.service.impl.DeviceCommandServiceImpl;
import com.pct.reciever.models.ReceivedUDPPacket;
import com.pct.reciever.models.ReportHeader;

@Component
public class CampaignManager {

	@Autowired
	private DeviceCommandServiceImpl deviceCommandservice;

	public void getATCommandFromRuleEngine(ReportHeader reportHeader, ReceivedUDPPacket receivedUDPPacket) {

		String device_id = reportHeader.getDeviceID();
//
		String at_command = "AT+XIP";
//		    
		String source = "Campaign manager";
//
//		    private String priority;
//		    
//		    private Instant last_executed;
//		    
//		    private boolean success;
//		    
		byte[] packet = receivedUDPPacket.getPacket();
		DeviceCommandRequest commandRequest = new DeviceCommandRequest(device_id, at_command, source, packet,
				receivedUDPPacket.getListenerIPAddress(), receivedUDPPacket.getListenerPort());
		deviceCommandservice.saveDeviceCommand(commandRequest);
	}
}
