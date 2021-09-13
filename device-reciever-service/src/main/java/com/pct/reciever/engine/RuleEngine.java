package com.pct.reciever.engine;

import java.net.DatagramPacket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pct.reciever.models.ReceivedUDPPacket;
import com.pct.reciever.models.ReportHeader;

@Component
public class RuleEngine {

	@Autowired
	private CampaignManager campaignManager;

	public void checkIfMaintenanceReport(ReportHeader reportHeader, ReceivedUDPPacket receivedUDPPacket) {

		// if(reportHeader.getEventID()==34) {
		if (true) {
			campaignManager.getATCommandFromRuleEngine(reportHeader, receivedUDPPacket);
		}
	}
}
