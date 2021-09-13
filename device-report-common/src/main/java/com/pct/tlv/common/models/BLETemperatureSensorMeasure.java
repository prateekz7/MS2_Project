package com.pct.tlv.common.models;

import java.util.List;

import lombok.Data;

@Data
public class BLETemperatureSensorMeasure {
	public String sensorId;
	private Integer size;
	public String sensorStatus;
	public String type;
	public String battery;
	public List<DataStart> dataStart;
	public List<DataReading> dataReading;
}
