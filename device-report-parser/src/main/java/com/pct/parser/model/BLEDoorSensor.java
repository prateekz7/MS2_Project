package com.pct.parser.model;

import org.springframework.stereotype.Component;

@Component
public class BLEDoorSensor extends BaseSensor {
	public String commStatus;
	public String sensorStatus;
	public String doorState;
	public String doorType;
	public String em;
	public String battery;
	public Integer temperatureC;
	public Float temperatureF;
	public String doorSequence;

}
