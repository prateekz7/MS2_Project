package com.pct.tlv.common.models;

import java.util.List;

import lombok.Data;

@Data
public class PSIAirSupply {
	public int asmsCount;
	public String commStatus;
	public List<PSIAirSupplyMeasure> PSIAirSupplInfomeasure;
}
