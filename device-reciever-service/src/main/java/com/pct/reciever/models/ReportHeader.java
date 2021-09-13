package com.pct.reciever.models;

import java.sql.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReportHeader {
	public Timestamp timestampReceived;
	public boolean acknowledge;
	public String deviceID;
	public int sequenceNumber;
	public int eventID;
	public String deviceIPAddress = null;
	public int devicePort;
	public int serverPort;
	public String serverIPAddress = null;
	public String rawReport;

}
