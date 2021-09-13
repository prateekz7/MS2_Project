package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class BLEDoorSensor {
	private String cStatus;
	private String sStatus;
	private String dState;
	private String type;
	private String em;
	private String battery;
	private Integer temperatureC;
	private Float temperatureF;
}
