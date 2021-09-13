package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class NetworkField {
	public int RSSI = 1000000000;
	public String deviceID;
	public int TLVNetworkSelector;
	public int payloadFieldsMask;
	public java.sql.Timestamp timestampReceived;	
}
