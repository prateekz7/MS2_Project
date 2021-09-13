package com.pct.tlv.common.models;

import java.util.List;

import lombok.Data;

@Data
public class SFKWheelEnd {
	public String commStatus;
	public List<SFKWheelEndMeasure> SFKWheelEndmeasure;
}
