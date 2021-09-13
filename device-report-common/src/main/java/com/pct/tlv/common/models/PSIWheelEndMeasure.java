package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class PSIWheelEndMeasure {
	public String leftStatus;
    public String leftBattery;
    public String rightStatus;
    public String rightBattery;
    public String leftTemperature;
    public String rightTemperature;
}
