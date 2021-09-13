package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class PSIAirSupplyMeasure {
	public String tankStatus;
	public String tankBattery;
    public String supplyStatus;
    public String supplyBattery;
    public String tankPressure;
    public String supplyPressure;
}
