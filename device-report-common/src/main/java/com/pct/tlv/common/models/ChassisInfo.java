package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class ChassisInfo {
	public String condition;
	public String cargo_state;
	public int distMM = -1;
	public int ageSeconds = 0;
	public int code1;
	public int code2;
}
