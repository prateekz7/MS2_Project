package com.pct.tlv.common.models;

import com.pct.tlv.common.utils.TimeUtilities;

import lombok.Data;

@Data
public class GeneralMaskFields {
	public static final int MIN_INDEX_VALUE = -1;
	private int TLVNetworkSelector;
	private int batteryVoltage1mV = 1000000000;
	private int GPSStatusIndex = -1;
	private int numSatellites = 1000000000;
	private int HDOP = 1000000000;
	private int GPIOStatus = 1000000000;
	private java.sql.Timestamp timestampReceived;
	private int primaryExternalVoltage1mV = 1000000000;
	private boolean isParsedTLVVolatge = false;
	private int generalExtVoltage1mV = 1000000000;
	private int generalInterVoltage1mV = 1000000000;
	private int TLVNumVoltages = 1000000000;
	private float odometerMiles = 1000000000;
	private long latitude = 1000000000;
	private long longitude = 1000000000;
	private float altitudeFeet = 1000000000;
	private int payloadFieldsMask;
	private float speedMiles = 1000000000;
	private int heading = 1000000000;
	private int RSSI = 1000000000;
	private TimeUtilities.DateTimeInfo GPSDateTimeInfo = null;
}
