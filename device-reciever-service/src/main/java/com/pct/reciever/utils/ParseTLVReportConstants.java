package com.pct.reciever.utils;

import java.util.HashMap;

public class ParseTLVReportConstants {
	public static final int FRAME_CODE_7E = 0x7E;
	public static final int FRAME_CODE_7D = 0x7D;

	public static final int DEVICE_ID_INDEX = 2;

	public static final int TLV_FIELD_LOCATION_AND_STATUS = 0x0000;
	public static final int TLV_FIELD_GENERAL = 0x0001;
	public static final int TLV_FIELD_NETWORK = 0x0011;
	public static final int TLV_FIELD_NETWORK_N = 0x0012;

	public static final int TLV_FIELD_ACCELEROMETER_DATA = 0x0013;
	public static final int TLV_FIELD_COMPRESSED_GPS = 0x0015;

	public static final int TLV_FIELD_ODOMETER = 0x0041;
	public static final int TLV_FIELD_GPIO = 0x050;
	public static final int TLV_FIELD_VERSION = 0x0100;
	public static final int TLV_FIELD_PERIPHERAL = 0x0101;
	public static final int TLV_FIELD_CONFIG_VERSION = 0x0102;
	public static final int TLV_FIELD_MLABS_VERSIONS = 0x0104;
	public static final int TLV_FIELD_TFTP = 0x0200;
	public static final int TLV_FIELD_VOLTAGE = 0x0210;
	public static final int TLV_FIELD_VEHICLE_VIN = 0x0401;
	public static final int TLV_FIELD_VEHICLE_DTC = 0x0402;
	public static final int TLV_FIELD_VEHICLE_ECU = 0x0403;
	public static final int TLV_FIELD_REMOTE_START_STATUS = 0x0410;
	public static final int TLV_FIELD_REMOTE_START_RUN_SEC = 0x0411;
	public static final int TLV_FIELD_REMOTE_START_RESPONSE = 0x0412;

	public static final int TLV_FIELD_REMOTE_START_YELLOW_ALARM_RESPONSE = 0x0414;

	public static final int TLV_FIELD_CHASSIS_MATE_SENSOR = 0x0440;
	public static final int TLV_FIELD_CARGO_SENSOR = 0x0441;
	public static final int TLV_FIELD_ORIENTATION_SENSOR = 0x0442;

	public static final int TLV_FIELD_DOOR_SENSOR = 0x0608;
	public static final int TLV_FIELD_LIGHT_SENTRY_SENSOR = 0x0610;
	public static final int TLV_FIELD_ABS_SENSOR = 0x0611;
	public static final int TLV_FIELD_ABS_ODOMETER_SENSOR = 0x0612;
	public static final int TLV_FIELD_TIRE_INFLATOR_SENSOR = 0x0613;
	public static final int TLV_FIELD_TIRES_SENSOR = 0x0614;
	public static final int TLV_FIELD_BRAKE_STROKE_SENSOR = 0x0615;
	public static final int TLV_FIELD_TRACTOR_PAIRING_SENSOR = 0x0625;
	public static final int TLV_FIELD_GLADHANDS_SENSOR = 0x0626;

	public static final int TLV_FIELD_NMEA_DATA = 0x1000;

	public static final int TLV_FIELD_INTERNAL_TEMPERATURE = 0x1001;

	public static final int TLV_FIELD_DIAGNOSTICS = 0x1fff;

	public static final int TLV_FIELD_BETA_ABS = 0x0621;
	public static final int TLV_WATERFALL_1 = 0x0103;
	public static final int TLV_WATERFALL_2 = 0x0259;
	public static final int TLV_PSI_WHEEL_END_MONITORING_SYSTEM = 0x0627;
	public static final int TLV_PSI_AIR_SUPPLY_MONITORING_SYSTEM = 0x062A;
	public static final int TLV_BLE_TEMPERATURE_SENSOR = 0x062B;
	public static final int TLV_FIELD_BETA_TPMS = 0x620;// 0x0609;
	public static final int TLV_FIELD_BETA_ABS_ID = 0x0624;
	public static final int TLV_FIELD_BLE_DOOR_SENSOR = 0X0628;
	public static final int TLV_FIELD_SFK_WHEEL_END = 0x0629;
	public static final int TLV_FIELD_REEFER = 0x62C;

	public static final HashMap<Integer, String> FLEXIBLE_REPORT_FIELD_TYPES = new HashMap<Integer, String>();

	static {
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_LOCATION_AND_STATUS, "Locaction and Status");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_GENERAL, "General");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_NETWORK, "Network");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_NETWORK_N, "Network Neighbors");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_ACCELEROMETER_DATA, "Accelerometer Data");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_COMPRESSED_GPS, "Compressed GPS");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_ODOMETER, "Odometer km");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_GPIO, "GPIO");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_VERSION, "Version");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_PERIPHERAL, "Peripheral");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_CONFIG_VERSION, "Config");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_MLABS_VERSIONS, "MLabs Versions");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_TFTP, "TFTP");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_VOLTAGE, "Voltage");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_VEHICLE_VIN, "Vehicle VIN");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_VEHICLE_DTC, "Vehicle DTC");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_VEHICLE_ECU, "Vehicle ECU");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_REMOTE_START_STATUS, "Remote Start Status");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_REMOTE_START_RUN_SEC, "Remote Start Run Sec");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_REMOTE_START_RESPONSE, "Remote Start Response");

		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_REMOTE_START_YELLOW_ALARM_RESPONSE, "Remote Start Yellow Response");

		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_CHASSIS_MATE_SENSOR, "Chassis Mate Sensor");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_CARGO_SENSOR, "Cargo Sensor");

		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_ORIENTATION_SENSOR, "Orientation Sensor");

		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_DOOR_SENSOR, "Door Sensor");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_LIGHT_SENTRY_SENSOR, "Light Sentry Sensor");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_ABS_SENSOR, "ABS Sensor");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_ABS_ODOMETER_SENSOR, "ABS Odometer Sensor");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_TIRE_INFLATOR_SENSOR, "Tire Inflator Sensor");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_TIRES_SENSOR, "Tires Sensor");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_BRAKE_STROKE_SENSOR, "Brake Stroke Sensor");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_TRACTOR_PAIRING_SENSOR, "Tractor Pairing Sensor");
		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_GLADHANDS_SENSOR, "Gladhand Sensor");

		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_NMEA_DATA, "NMEA Data");

		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_INTERNAL_TEMPERATURE, "Internal Temperature");

		FLEXIBLE_REPORT_FIELD_TYPES.put(TLV_FIELD_DIAGNOSTICS, "Diagnostics");
	}

	public static final int TAG_INNDEX = 1;
	public static final int TAG_LEN = 1;
	public static final byte LENGTH_1_2_MASK = (byte) 0b11100000;
	public static final int SEQUENCE_NUM_LENGTH = 2;
	public static final int UTC_TIMESTAMP_LENGTH = 4;
	public static final int UTC_TIMESTAMP_YEAR_OFFSET = 2000;
	public static final byte ACK_MASK = 0b011;
	public static final int TAG_SHIFT = 4;
	public static final int DIAGNOSTICS_LIGHT_LEN = 1;
	public static final int DIAGNOSTICS_CODES_LEN_LEN = 1;

	/////////////////////////////////////// TLV General Masked Fields
	/////////////////////////////////////// //////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static final int GPS_STATUS_SHIFT = 8;
	public static final int GPS_STATUS_MASK = 0x01;
	public static final int RSSI_MASK = 0x7f;
	public static final int SATELLITE_NUM_MASK = 0x0f;
	public static final int LON_LAT_LENGTH = 4;

	public static final int TLV_GENERAL_MASK_FIELD_LEN = 1;
	public static final int NUM_TLV_GENERAL_FIELD_TYPES = 8;

	public static final int SPEED_HEADING_CASE = 0;
	public static final int ALTITUDE_CASE = 1;
	public static final int NUM_SAT_HDOP_CASE = 2;
	public static final int EXT_INT_VOLTAGE_CASE = 3;
	public static final int GPIO_STATUS_CASE = 4;
	public static final int ODOMETER_CASE = 5;

	public static final int NUM_TLV_NETWORK_FIELD_TYPES = 8;
//
	public static final int SERVICE_CASE = 0;
	public static final int MCC_MNC_CASE = 1;
	public static final int TOWER_ID_CASE = 2;
	public static final int TOWER_CENTROID_CASE = 3;
	public static final int BAND_CASE = 4;
	public static final int RSSI_CASE = 5;
	public static final int RX_TX_EC_CASE = 6;

	public static final int ROAMING_MASK = 0x01;
	public static final int ROAMING_SHIFT = 0x08;
	public static final int SERVICE_TYPE_MASK = 0x7f;
	public static final int MOBILE_COUNTRY_CODE_LEN = 2;
	public static final int MOBILE_NETWORK_CODE_LEN = 2;

	/////////////////////////////////////////////////// Various other TLVs
	/////////////////////////////////////////////////// /////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static final int PERIPHERAL_PORT_TYPE_MASK = 0x0f;
	public static final int PERIPHERAL_PORT_TYPE_SHIFT = 0x04;
	public static final int PERIPHERAL_DRIVER_LEN = 1;

	public static final int VOLTAGE_LEN = 2;

}
