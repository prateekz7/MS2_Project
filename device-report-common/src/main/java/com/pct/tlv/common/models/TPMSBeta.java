package com.pct.tlv.common.models;

import java.util.List;

import lombok.Data;

@Data
public class TPMSBeta {
	public String status;
	public int numSensors;
	public List<TPMSBetaMeasure> TPMSBetameasure;
}
