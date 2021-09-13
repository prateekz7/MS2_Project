package com.pct.parser.response;

import com.pct.parser.model.BLEDoorSensor;
import com.pct.parser.model.Voltage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {

	private BLEDoorSensor bleDoorSensor;
	private Voltage voltage;

}
