package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class ABSBetaMeasure {
	public String fmiStatus;
	public String activeStatus;
	public String pidType;
	public String sidPidStatus;
	public int fmi;
	public int sidPid;
	public int occurrenceCount;
}
