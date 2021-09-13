package com.pct.tlv.common.models;

import java.util.List;

import lombok.Data;

@Data
public class PSIWheelEnd {
	public String commStatus;
	public int axleElements;
	public List<PSIWheelEndMeasure> PSIWheelEndmeasure;
}
