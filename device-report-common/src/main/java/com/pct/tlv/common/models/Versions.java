package com.pct.tlv.common.models;

import lombok.Data;

@Data
public class Versions {
	public int hwVersionAssembly = 1000000000;
	public int hwVersionRevision = 1000000000;
	public String extenderVersion;
	public String swVersionBaseband;
	public String swVersionApplication;
	public String deviceName;
	public String bleVersion;
}
