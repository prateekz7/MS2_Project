package com.pct.tlv.common.models;

import java.util.List;

import lombok.Data;

@Data
public class BLETemperatureSensor {
	public String commStatus;
	private List<BLETemperatureSensorMeasure> BLETemperatureSensormeasure;
}
