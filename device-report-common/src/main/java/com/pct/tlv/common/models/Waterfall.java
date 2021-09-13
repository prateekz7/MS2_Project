package com.pct.tlv.common.models;

import java.util.List;

import lombok.Data;

@Data
public class Waterfall {
	public int numberOfFiles;
	public List<Integer> configId;
	public List<String> configCRC;
	public List<String> configIdentificationVersion;
}
