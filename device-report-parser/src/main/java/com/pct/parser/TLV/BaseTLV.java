package com.pct.parser.TLV;

import java.nio.ByteOrder;

public abstract class BaseTLV<T> {

	public static final int MAX_INT_NUMBER_VALUE = 1000000000;
	protected byte[] report;
	protected boolean hasData;
	protected int payloadIndex;
	protected int valueLength;
	protected ByteOrder byteOrder;

	public void setData(byte[] report, int payloadIndex, int valueLength) {
		this.report = report;
		this.payloadIndex = payloadIndex;
		this.valueLength = valueLength;
		byteOrder = ByteOrder.BIG_ENDIAN;
	}

	public byte[] getReport() {
		return report;
	}

	public void setReport(byte[] report) {
		this.report = report;
	}

	public int getPayloadIndex() {
		return payloadIndex;
	}

	public void setPayloadIndex(int payloadIndex) {
		this.payloadIndex = payloadIndex;
	}

	public boolean isHasData() {
		return hasData;
	}

	public void setHasData(boolean hasData) {
		this.hasData = hasData;
	}

	public abstract void toJson();

	public abstract T parseSensor();

}
