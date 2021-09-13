package com.pct.parser.model;

import org.springframework.stereotype.Component;

@Component
public class Voltage extends BaseSensor {

	public int TLVNumVoltages = 1000000000;
	public int mainPower = 1000000000;
	public int auxPower = 1000000000;
	public int chargePower = 1000000000;
	public int batteryPower = 1000000000;
}
