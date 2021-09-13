package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class ABSBetaId {
	public String status;
	public String manufacturerInfo;
    public int serialNum;
    public int absFwVersion;
    public int model;
}
