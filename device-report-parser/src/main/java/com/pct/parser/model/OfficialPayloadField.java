package com.pct.parser.model;

public class OfficialPayloadField {
	private String fieldName;
	private int fieldLength;
	private byte[] subFieldsLength;

	public OfficialPayloadField(String fieldName, int fieldLength, byte[] subFieldsLength) {
		this.fieldName = fieldName;
		this.fieldLength = fieldLength;
		this.subFieldsLength = subFieldsLength;
	}

	public String getFieldName() {
		return fieldName;
	}

	public int getFieldLength() {
		return fieldLength;
	}

	public byte[] getSubFieldsLength() {
		return subFieldsLength;
	}

}
