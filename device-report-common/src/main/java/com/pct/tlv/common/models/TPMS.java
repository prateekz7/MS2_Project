package com.pct.tlv.common.models;

import java.util.List;

import lombok.Data;

@Data
public class TPMS {
	public String status;
	public int numSensors;
	public List<TPMSMeasure> TPMSmeasure;
}
