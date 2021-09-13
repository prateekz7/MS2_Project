package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class Voltage {

	public int TLVNumVoltages = 1000000000;
	public int mainPower =1000000000;
	public int auxPower =1000000000;
	public int chargePower =1000000000;
	public int batteryPower =1000000000;	
}
