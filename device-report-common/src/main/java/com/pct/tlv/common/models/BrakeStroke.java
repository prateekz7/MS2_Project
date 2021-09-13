package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class BrakeStroke {
		
	public boolean notCommunicating;
	public boolean noFaults;
	public int undefinedError;
	public boolean leftFrontParkingSpringEngaged;
	public boolean rightFrontParkingSpringEngaged;
	public boolean leftRearParkingSpringEngaged;
	public boolean rightRearParkingSpringEngaged;
	public int leftFrontBrakeStrokNum = 0;
	public int rightFrontBrakeStrokNum = 0;
	public int leftRearBrakeStrokNum = 0;
	public int rightRearBrakeStrokNum = 0;
}
