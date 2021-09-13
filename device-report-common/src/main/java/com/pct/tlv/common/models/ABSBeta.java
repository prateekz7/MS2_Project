package com.pct.tlv.common.models;

import java.util.List;

import lombok.Data;

@Data
public class ABSBeta {
	public String status;
	public int numSensors;
	public float absVoltage;
	public String reserved;
	public int absWarningLamp;
	public String absWarningLampStatus;
	public int numOfFaults;
	public boolean isAnyFaultActive;
	public List<ABSBetaMeasure> ABSBetaMeasure;	
}
