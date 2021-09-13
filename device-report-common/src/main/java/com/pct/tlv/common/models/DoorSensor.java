package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class DoorSensor {
	public String status;
	public int RSSI;
	public int openCtr;
	public String HWVer;
    public String lastValidCheck;
}
