package com.pct.parser.TLV;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteOrder;

import org.apache.kafka.common.serialization.Serializer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pct.parser.utils.EventsConstants;
import com.pct.parser.utils.Numbers;
import com.pct.parser.utils.ParseTLVReportConstants;
import com.pct.parser.utils.StringUtilities;
import com.pct.parser.utils.TimeUtilities;

//@NoArgsConstructor
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ParseRawReport implements Serializable, Serializer {

	private DeviceReport deviceReport;

	private static final long serialVersionUID = 1744050117179344127L;

	@Override
	public byte[] serialize(String s, Object o) {
		return new byte[0];
	}

	private byte[] report;
	private ByteOrder byteOrder;
	private String rawReport;
	// length constants are in bytes

	private boolean acknowledge;

	private TimeUtilities.DateTimeInfo RTCDateTimeInfo = null;

	public static final int GENERAL_ERROR = -1;

	private int completeFrameLength;
	private String deviceTag;
	private int eventTypeIndex;

	public ParseRawReport(byte[] report, ByteOrder byteOrder) throws IOException {

		this.report = report;
		this.byteOrder = byteOrder;
		rawReport = StringUtilities.Byte2HexString(report);
		ParseReportHeaderOnly();
	}

	public void initilize(byte[] report, ByteOrder byteOrder, String verticalName, DeviceReport deviceReport)
			throws IOException {

		// this.databaseConnection = databaseConnection;
		this.report = report;
		this.byteOrder = byteOrder;
		rawReport = StringUtilities.Byte2HexString(report);
		this.deviceReport = deviceReport;
		parseReportBinary();
	}

	public ParseRawReport() {

	}

	private void parseReportBinary() throws IOException {

		if ((report == null) || (report.length == 0)) {
			throw new IOException("Empty Report!");
		}

		int frameCode = Numbers.parseInt(report, ParseTLVReportConstants.FRAME_CODE_INDEX,
				ParseTLVReportConstants.FRAME_CODE_LEN, this.byteOrder);

		switch (frameCode) {

		case ParseTLVReportConstants.FRAME_CODE_7E:
			// Monet report - masked and has Start Of Frame code 7E
			///////////////////////////////////////////////////////
			// parse7EStartOfFrameReport();
			break;
		case ParseTLVReportConstants.FRAME_CODE_7D:
			// Flexible TLV (new) report
			////////////////////////////
			parse7DStartOfFrameReport();
			break;
		default:
			break;
		}

	}

	public void ParseReportHeaderOnly() throws IOException {

		if ((report == null) || (report.length == 0)) {
			throw new IOException("Empty Report!");
		}

		int frameCode = Numbers.parseInt(report, ParseTLVReportConstants.FRAME_CODE_INDEX,
				ParseTLVReportConstants.FRAME_CODE_LEN, this.byteOrder);

		switch (frameCode) {

		case ParseTLVReportConstants.FRAME_CODE_7E:
			// Monet report - masked and has Start Of Frame code 7E
			///////////////////////////////////////////////////////
			// Parese7EStartOfFrameHeader();
			break;
		case ParseTLVReportConstants.FRAME_CODE_7D:
			// Flexible TLV (new) report
			////////////////////////////
			// Parse7DStartOfFrameHeader();
			break;
		default:
			break;
		}

	}

	/**
	 * This method invokdes the header parser for the new TLV report type with 7D
	 * frame code and then invokdes the payload parser.
	 */
	private void parse7DStartOfFrameReport() {

		int payloadIndex = Parse7DStartOfFrameHeader();
		if (payloadIndex != GENERAL_ERROR) {
			parseTLVPayload(payloadIndex);
		}

	}

	public static final int CDMA = 1;

	public int Parse7DStartOfFrameHeader() {
		String deviceID = null;
		int payloadIndex = 1;
		int fieldLen;
		try {
			byte b;

			byte tag = report[payloadIndex];

			acknowledge = (tag & ParseTLVReportConstants.ACK_MASK) != 0;
			payloadIndex++;

			b = report[payloadIndex];
			deviceTag = ParseTLVReportConstants.DEVICE_TYPES[b >>> ParseTLVReportConstants.TAG_SHIFT];
			deviceID = StringUtilities.Byte2HexString(report, payloadIndex, ParseTLVReportConstants.DEVICE_ID_LEN);
			if (deviceTag.equalsIgnoreCase(ParseTLVReportConstants.DEVICE_TYPES[CDMA])) {

				deviceID = deviceID.substring(2);

			} else {
				deviceID = deviceID.substring(1);
			}
			payloadIndex += ParseTLVReportConstants.DEVICE_ID_LEN;

			int sequenceNumber = Numbers.parseInt(report, payloadIndex, ParseTLVReportConstants.SEQUENCE_NUM_LENGTH,
					this.byteOrder);
			payloadIndex += ParseTLVReportConstants.SEQUENCE_NUM_LENGTH;

			RTCDateTimeInfo = parseTLVDateTime(report, payloadIndex);

			payloadIndex += ParseTLVReportConstants.UTC_TIMESTAMP_LENGTH;
		} catch (Exception e) {

			payloadIndex = GENERAL_ERROR;
			return payloadIndex;
		}

		// next 1 or 2 bytes is/are the report event information.
		// If the 3 MSBs are on then we have 2 bytes for length otherwise a single byte
		///////////////////////////////////////////////////////////////////////////////
		byte[] fieldBytes = getOneOrTwoBytes(report, payloadIndex);
		payloadIndex = parseEvent(fieldBytes, payloadIndex);

		// next 1 or 2 bytes is/are the report payload length information.
		// If the 3 MSBs are on then we have 2 bytes for length otherwise a single byte
		///////////////////////////////////////////////////////////////////////////////
		fieldBytes = getOneOrTwoBytes(report, payloadIndex);
		fieldLen = fieldBytes.length;
		int frameLengthFromReport;

		// now we can parse the unsigned length
		///////////////////////////////////////
		if (fieldLen > 0) {
			frameLengthFromReport = Numbers.parseInt(fieldBytes, 0, fieldBytes.length, this.byteOrder);
			payloadIndex += fieldLen;

			completeFrameLength = frameLengthFromReport + payloadIndex;
		} else {
			frameLengthFromReport = 0;
			completeFrameLength = rawReport.length();
			/*
			 * Messages.
			 * LogListenerErrorsToFile("Parse7DStartOfFrameHeader: Report Length 0 Error: Device "
			 * + " payloadIndex: " + payloadIndex + " filedLength: " + fieldLen +
			 * " reportLength: " + report.length + " at: " + timestampReceived);
			 */
			payloadIndex = GENERAL_ERROR;
		}

		return payloadIndex;

	}

	private int parseEvent(byte[] fieldBytes, int payloadIndex) {
		String eventType = null;
		if ((fieldBytes == null) || (fieldBytes.length == 0)) {
			eventType = "ERROR";
			return payloadIndex;
		}

		int index;
		int fieldLen = fieldBytes.length;

		if (fieldLen == 1) {
			eventTypeIndex = (int) fieldBytes[0] & 0xFF;
		} else {
			eventTypeIndex = Numbers.parseInt(fieldBytes, 0, fieldLen, byteOrder);
		}

		eventType = StringUtilities.Byte2HexString(fieldBytes);

		if ((eventTypeIndex >= 0) && (eventTypeIndex < EventsConstants.EVENT_TYPES.length)) {
			eventType = EventsConstants.EVENT_TYPES[eventTypeIndex];

			// check if event needs refreshing from the DB.
			//////////////////////////////////////////////
			try {
				index = Integer.parseInt(eventType, 16);
				if (index == eventTypeIndex) {
					// DeviceEvent de = EventDBWorker.GetEventByIDFromDB(eventTypeIndex, 1,
					// databaseConnection);
					/*
					 * DeviceEvent[] de = EventDBWorker.GetEventByIDFromDB(eventTypeIndex,
					 * dataSource, true, 1); if ((de != null) && (de.length > 0) && (de[0] != null))
					 * { EventsConstants.EVENT_TYPES[eventTypeIndex] = de[0].getEventType();
					 * Messages.LogListenerEventsToFile("New event: " + index + " fetched from DB: "
					 * + de[0].getEventType()); } else {
					 * Messages.LogListenerEventsToFile("New event: " + index + " not found in DB");
					 * }
					 */
				}
			} catch (NumberFormatException e) {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ((eventType == null) || (eventType.isEmpty()) || (eventType.equalsIgnoreCase("undefined"))
				|| (eventType.equalsIgnoreCase("reserved")) || (eventType.equalsIgnoreCase("null"))) {
			eventType = Integer.toHexString(eventTypeIndex);
		}

		payloadIndex += fieldLen;
		return payloadIndex;
	}

	private TimeUtilities.DateTimeInfo parseTLVDateTime(byte[] report, int payloadIndex) {

		long timestamp = Numbers.parseLong(report, payloadIndex, ParseTLVReportConstants.UTC_TIMESTAMP_LENGTH,
				this.byteOrder);

		TimeUtilities.DateTimeInfo dateTimeInfo = new TimeUtilities.DateTimeInfo();
		dateTimeInfo.year = ParseTLVReportConstants.UTC_TIMESTAMP_YEAR_OFFSET
				+ (int) (timestamp / (60 * 60 * 24 * 31 * 12));
		dateTimeInfo.month = 1 + (int) (timestamp / (60 * 60 * 24 * 31)) % 12;
		dateTimeInfo.day = 1 + (int) (timestamp / (60 * 60 * 24)) % 31;
		dateTimeInfo.hour = (int) (timestamp / (60 * 60)) % 24;
		dateTimeInfo.minutes = (int) (timestamp / 60) % 60;
		dateTimeInfo.seconds = (int) (timestamp % 60);

		return dateTimeInfo;
	}

	/**
	 * Parsing method for Type Length Value (TLV) fields
	 * 
	 * @param payloadIndex - offste in to the raw report where TLV fields start
	 */
	private void parseTLVPayload(int payloadIndex) {
		byte b;
		byte[] TLBytes;
		int valueLength;
		int valueTag;

		while (payloadIndex < (completeFrameLength - 2)) {

			// isParsedTLVVolatge = false;
			// next 1 or 2 bytes is/are the TLV tag information.
			// If the 3 MSBs are on then we have 2 bytes for length otherwise a single byte
			///////////////////////////////////////////////////////////////////////////////
			TLBytes = getOneOrTwoBytes(report, payloadIndex);

			// now we can parse the unsigned tag. If there is no tag we will still try to
			// read the lenght
			////////////////////////////////////////////////////////////////////////////////////////////
			if (TLBytes.length > 0) {
				valueTag = Numbers.parseInt(TLBytes, 0, TLBytes.length, this.byteOrder);
				payloadIndex += TLBytes.length;
			} else {
				valueTag = Integer.MAX_VALUE;
			}

			// next 1 or 2 bytes is/are the TLV length information.
			// If the 3 MSBs are on then we have 2 bytes for length otherwise a single byte
			///////////////////////////////////////////////////////////////////////////////
			TLBytes = getOneOrTwoBytes(report, payloadIndex);
			// now we can parse the unsigned length. If there is no length we will exit the
			// parsing since
			// we can provide increment for the next TLV
			//////////////////////////////////////////////////////////////////////////////////////////////
			if (TLBytes.length > 0) {
				valueLength = Numbers.parseInt(TLBytes, 0, TLBytes.length, this.byteOrder);
				payloadIndex += TLBytes.length;
			} else {

				return;
			}

			if (valueLength > 0) {
				try {
					switch (valueTag) {
					case ParseTLVReportConstants.TLV_FIELD_LOCATION_AND_STATUS:
						break;
					case ParseTLVReportConstants.TLV_FIELD_GENERAL:
						System.out.println("General TlV DATA");
						break;

					case ParseTLVReportConstants.TLV_FIELD_NETWORK:
						System.out.println("Network field TlV DATA");
						break;

					case ParseTLVReportConstants.TLV_FIELD_NETWORK_N:

						break;
					case ParseTLVReportConstants.TLV_FIELD_ACCELEROMETER_DATA:
						break;
					case ParseTLVReportConstants.TLV_FIELD_ODOMETER:

						break;
					case ParseTLVReportConstants.TLV_FIELD_VERSION:
						System.out.println("PERIPHERAL Vesion TLV");
						break;

					case ParseTLVReportConstants.TLV_FIELD_PERIPHERAL:
						// peripherals = new byte[valueLength];
						// System.arraycopy(report, payloadIndex, peripherals, 0, valueLength);
						break;

					case ParseTLVReportConstants.TLV_FIELD_CONFIG_VERSION:
						break;
					case ParseTLVReportConstants.TLV_FIELD_GPIO:

						break;
					case ParseTLVReportConstants.TLV_FIELD_TFTP:
						break;
					case ParseTLVReportConstants.TLV_FIELD_VOLTAGE:
						System.out.println("TLV_FIELD_VOLTAGE");
					deviceReport.voltage.setData(report, payloadIndex, valueLength);
						break;
					case ParseTLVReportConstants.TLV_FIELD_VEHICLE_VIN:
						break;
					case ParseTLVReportConstants.TLV_FIELD_VEHICLE_DTC:
						break;
					case ParseTLVReportConstants.TLV_FIELD_VEHICLE_ECU:
						break;
					case ParseTLVReportConstants.TLV_FIELD_REMOTE_START_STATUS:
						break;
					case ParseTLVReportConstants.TLV_FIELD_REMOTE_START_RUN_SEC:
						break;
					case ParseTLVReportConstants.TLV_FIELD_REMOTE_START_RESPONSE:
						// todo
						break;
					case ParseTLVReportConstants.TLV_FIELD_REMOTE_START_YELLOW_ALARM_RESPONSE:
						break;

					case ParseTLVReportConstants.TLV_FIELD_CHASSIS_MATE_SENSOR:
						System.out.println("TLV_FIELD_CHASSIS_MATE_SENSOR");
						break;

					case ParseTLVReportConstants.TLV_FIELD_CARGO_SENSOR:

						break;

					case ParseTLVReportConstants.TLV_FIELD_ORIENTATION_SENSOR:

						break;

					case ParseTLVReportConstants.TLV_FIELD_DOOR_SENSOR:
						break;

					case ParseTLVReportConstants.TLV_FIELD_LIGHT_SENTRY_SENSOR:
						System.out.println("TLV_FIELD_LIGHT_SENTRY_SENSOR");
						break;
					case ParseTLVReportConstants.TLV_FIELD_ABS_SENSOR:
						System.out.println("TLV_FIELD_ABS_SENSOR");
						break;
					case ParseTLVReportConstants.TLV_FIELD_ABS_ODOMETER_SENSOR:
						System.out.println("TLV_FIELD_ABS_ODOMETER_SENSOR");
						break;

					case ParseTLVReportConstants.TLV_FIELD_TIRE_INFLATOR_SENSOR:
						System.out.println("TLV_FIELD_TIRE_INFLATOR_SENSOR");
						break;
					case ParseTLVReportConstants.TLV_FIELD_TIRES_SENSOR:
						break;
					case ParseTLVReportConstants.TLV_FIELD_BRAKE_STROKE_SENSOR:
						break;
					case ParseTLVReportConstants.TLV_FIELD_TRACTOR_PAIRING_SENSOR:

						break;
					case ParseTLVReportConstants.TLV_FIELD_GLADHANDS_SENSOR:
						break;

					case ParseTLVReportConstants.TLV_FIELD_NMEA_DATA:
						break;

					case ParseTLVReportConstants.TLV_FIELD_COMPRESSED_GPS:

						break;

					case ParseTLVReportConstants.TLV_FIELD_INTERNAL_TEMPERATURE:

						break;

					case ParseTLVReportConstants.TLV_FIELD_DIAGNOSTICS:
						// todo
						break;

					case ParseTLVReportConstants.TLV_FIELD_BETA_ABS:
						System.out.println("TLV_FIELD_BETA_ABS");
						break;

					case ParseTLVReportConstants.TLV_WATERFALL_2:
						System.out.println("259");
						break;

					case ParseTLVReportConstants.TLV_WATERFALL_1:
						break;

					case ParseTLVReportConstants.TLV_PSI_WHEEL_END_MONITORING_SYSTEM:
						break;

					case ParseTLVReportConstants.TLV_PSI_AIR_SUPPLY_MONITORING_SYSTEM:
						System.out.println("psi air");

						break;

					case ParseTLVReportConstants.TLV_BLE_TEMPERATURE_SENSOR:
						System.out.println("ble temp");
						break;

					case ParseTLVReportConstants.TLV_FIELD_BETA_TPMS:
						break;

					case ParseTLVReportConstants.TLV_FIELD_BETA_ABS_ID:

						break;

					case ParseTLVReportConstants.TLV_FIELD_SFK_WHEEL_END:

						break;

					case ParseTLVReportConstants.TLV_FIELD_REEFER:
						System.out.println("TLV_FIELD_REEFER");
						break;

					case ParseTLVReportConstants.TLV_FIELD_BLE_DOOR_SENSOR:
						System.out.println("BLE Door Sensor");
						// deviceReport.getTlv("bleDoorSensor").setData(report, payloadIndex,
						// valueLength);
						deviceReport.bleDoorSensor.setData(report, payloadIndex, valueLength);

						break;

					default:
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();

				}
				payloadIndex += valueLength;
			} // end of value length > 0
			else {
				// handle length 0 in case it represents some info
				//////////////////////////////////////////////////
				switch (valueTag) {

				case ParseTLVReportConstants.TLV_FIELD_VEHICLE_DTC:
					// parseTLVDTC(report, payloadIndex, 0);
					break;
				default:
					break;
				}
			}
		}

	}

	public static byte[] getOneOrTwoBytes(byte[] report, int payloadIndex) {

		if ((report == null) || (payloadIndex >= report.length)) {
			return new byte[0]; // return empty array
		}
		byte[] fieldBytes;
		byte byteValue = report[payloadIndex];
		if (((byteValue & ParseTLVReportConstants.LENGTH_1_2_MASK) == ParseTLVReportConstants.LENGTH_1_2_MASK)
				&& ((payloadIndex + 1) < report.length)) {

			fieldBytes = new byte[2];
			System.arraycopy(report, payloadIndex, fieldBytes, 0, 2);
			fieldBytes[0] &= (byte) ~ParseTLVReportConstants.LENGTH_1_2_MASK;
		} else {
			fieldBytes = new byte[] { byteValue };
			System.arraycopy(report, payloadIndex, fieldBytes, 0, 1);
		}
		return fieldBytes;
	}

	public byte[] getReport() {
		return report;
	}

	public void setReport(byte[] report) {
		this.report = report;
	}

	public ByteOrder getByteOrder() {
		return byteOrder;
	}

	public void setByteOrder(ByteOrder byteOrder) {
		this.byteOrder = byteOrder;
	}

	public String getRawReport() {
		return rawReport;
	}

	public void setRawReport(String rawReport) {
		this.rawReport = rawReport;
	}

	public boolean isAcknowledge() {
		return acknowledge;
	}

	public void setAcknowledge(boolean acknowledge) {
		this.acknowledge = acknowledge;
	}

	public TimeUtilities.DateTimeInfo getRTCDateTimeInfo() {
		return RTCDateTimeInfo;
	}

	public void setRTCDateTimeInfo(TimeUtilities.DateTimeInfo rTCDateTimeInfo) {
		RTCDateTimeInfo = rTCDateTimeInfo;
	}

	public int getCompleteFrameLength() {
		return completeFrameLength;
	}

	public void setCompleteFrameLength(int completeFrameLength) {
		this.completeFrameLength = completeFrameLength;
	}

	public String getDeviceTag() {
		return deviceTag;
	}

	public void setDeviceTag(String deviceTag) {
		this.deviceTag = deviceTag;
	}

	public int getEventTypeIndex() {
		return eventTypeIndex;
	}

	public void setEventTypeIndex(int eventTypeIndex) {
		this.eventTypeIndex = eventTypeIndex;
	}

}
