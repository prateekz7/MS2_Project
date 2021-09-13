package com.pct.reciever.services;

import java.net.InetAddress;
import java.nio.ByteOrder;

import com.pct.reciever.models.ReceivedUDPPacket;
import com.pct.reciever.models.ReportHeader;
import com.pct.reciever.utils.Constants;
import com.pct.reciever.utils.Numbers;
import com.pct.reciever.utils.ParseTLVReportConstants;
import com.pct.reciever.utils.StringUtilities;
import com.pct.reciever.utils.TimeUtilities;

public class Tokeniser {
	public static String ParseReportDeviceIDOnly(byte[] report) {

		String deviceID = null;
		if ((report == null) || (report.length == 0)) {
			return deviceID;
		}

		int frameCode = Numbers.parseInt(report, Constants.FRAME_CODE_INDEX, Constants.FRAME_CODE_LEN,
				ByteOrder.BIG_ENDIAN);

		switch (frameCode) {

		case Constants.FRAME_CODE_7E:

			try {
				int tag = Numbers.parseInt(report, Constants.DEVICE_TAG_INDEX, Constants.DEVICE_TAG_LEN,
						ByteOrder.BIG_ENDIAN);
				String deviceTag = Constants.DEVICE_TYPES[tag & Constants.TAG_MASK];

				deviceID = StringUtilities.Byte2HexString(report, Constants.DEVICE_ID_INDEX, Constants.DEVICE_ID_LEN);
				if (deviceTag.equalsIgnoreCase(Constants.DEVICE_TYPES[Constants.CDMA])) {

					deviceID = deviceID.substring(2);
				} else {
					deviceID = deviceID.substring(1);
				}
			} catch (Exception e) {
				deviceID = null;
			}
			break;
		case Constants.FRAME_CODE_7D:

			int payloadIndex = 1;
			try {
				byte b;
				byte tag = report[payloadIndex];
				payloadIndex++;
				b = report[payloadIndex];
				String deviceTag = Constants.DEVICE_TYPES[b >>> Constants.TAG_SHIFT];
				deviceID = StringUtilities.Byte2HexString(report, payloadIndex, Constants.DEVICE_ID_LEN);
				if (deviceTag.equalsIgnoreCase(Constants.DEVICE_TYPES[Constants.CDMA])) {
					deviceID = deviceID.substring(2);

				} else {
					deviceID = deviceID.substring(1);
				}
			} catch (Exception e) {
				deviceID = null;
			}
			break;
		default:
			deviceID = null;
			break;
		}
		return deviceID;
	}

//	public static byte[] GetAcknowledgePacket(boolean isCloseDeviceListenWindow, byte[] report, ByteOrder byteOrder,
//			String uuid) {
//
//		byte[] packet = null;
//		boolean acknowledge;
//		byte tag;
//		int sequenceNumber;
//		byte oddEvenFrame;
//		String deviceID;
//
//		byte frameCode = report[0];
//
//		if (frameCode == Constants.FRAME_CODE_7E) {
//			// 7E
//			/////
//			tag = report[Constants.DEVICE_TAG_INDEX];
//			acknowledge = (tag & Constants.ACK_MASK) != 0;
//			if (acknowledge) {
//				oddEvenFrame = (byte) (tag & Constants.ODD_EVEN_FRAME_MASK);
//				tag = (byte) (0x0F | oddEvenFrame); // Tag, even/odd, no identification
//				packet = new byte[] { Constants.START_OF_FRAME_7E_AND_TLV, Constants.RESERVED, Constants.PAYLOAD_LENGTH,
//						Constants.PROTOCOL_ID, tag };
//			}
//		} else if (frameCode == Constants.FRAME_CODE_7D) {
//			// 7D
//			/////
//			acknowledge = (report[Constants.TAG_INDEX] & Constants.ACK_MASK) != 0;
//			if (acknowledge) {
//				sequenceNumber = Numbers.parseInt(report, 1 + Constants.TAG_LEN + Constants.DEVICE_ID_LEN,
//						Constants.SEQUENCE_NUM_LENGTH, byteOrder);
//				if ((sequenceNumber & 0x0001) != 0) {
//					tag = (byte) 0x4F; // Tag, odd, no identification
//				} else {
//					tag = (byte) 0x0F; // Tag, even, no identification
//				}
//
//				String deviceTag = Constants.DEVICE_TYPES[report[ParseTLVReportConstants.TAG_INNDEX] >>> Constants.TAG_SHIFT];
//				deviceID = StringUtilities.Byte2HexString(report, ParseTLVReportConstants.DEVICE_ID_INDEX,
//						Constants.DEVICE_ID_LEN);
//
//				if (deviceTag.equalsIgnoreCase(Constants.DEVICE_TYPES[Constants.CDMA])) {
//					deviceID = deviceID.substring(2);
//
//				} else {
//					deviceID = deviceID.substring(1);
//				}
//				if (isCloseDeviceListenWindow) {
//					// no need to keep device in listen window since there is no UDP command waiting
//					////////////////////////////////////////////////////////////////////////////////
//					tag |= 0b00010000;
//					System.out.println("AcknowledgeUDPPacket.GetAcknowledgePacket: Message UUID : " + uuid
//							+ " preparing NACK to device = " + deviceID + " to stop listening:" + "   TAG = 0b"
//							+ leftPad(Integer.toBinaryString(tag), 8));
//				} else {
//					// keep device in listening window since there is a UDP command for the device
//					//////////////////////////////////////////////////////////////////////////////
//					System.out.println("AcknowledgeUDPPacket.GetAcknowledgePacket: Message UUID : " + uuid
//							+ " preparing ACK to device = " + deviceID + " to keep listening:" + "   TAG = 0b"
//							+ leftPad(Integer.toBinaryString(tag), 8));
//				}
//
//				packet = new byte[] { Constants.START_OF_FRAME_7E_AND_TLV, Constants.RESERVED, Constants.PAYLOAD_LENGTH,
//						Constants.PROTOCOL_ID, tag };
//			}
//		} else {
//
//			acknowledge = (report[Constants.ACKNOWLEDGE_INDEX] & Constants.TAG_MASK) != 0;
//			if (acknowledge) {
//				sequenceNumber = (int) report[Constants.SEQUENCE_NUM_INDEX] & 0xFF;
//				packet = new byte[] { Constants.START_OF_FRAME_TALON, (byte) sequenceNumber, Constants.RESERVED };
//			}
//		}
//
//		return packet;
//	}

	public static ReportHeader Parse7DStartOfFrameHeader(ReceivedUDPPacket receivedUDPPacket,
			String packetDestinationIP) {

		int payloadIndex = 1;
		int fieldLen;

		ReportHeader report7DHeader = new ReportHeader();
		report7DHeader.timestampReceived = receivedUDPPacket.getTimeStamp();
		InetAddress deviceIPAddress = receivedUDPPacket.getDeviceIPAddress();
		if (deviceIPAddress != null) {
			if (deviceIPAddress.getHostAddress() != null) {
				report7DHeader.deviceIPAddress = deviceIPAddress.getHostAddress();
			}
		}
		report7DHeader.devicePort = receivedUDPPacket.getDevicePort();
		report7DHeader.serverIPAddress = packetDestinationIP;
		report7DHeader.serverPort = receivedUDPPacket.getListenerPort();

		byte[] report = receivedUDPPacket.getPacket();
		try {
			byte b;

			byte tag = report[payloadIndex];

			report7DHeader.acknowledge = (tag & ParseTLVReportConstants.ACK_MASK) != 0;
			payloadIndex++;

			b = report[payloadIndex];
			String deviceType = Constants.DEVICE_TYPES[b >>> ParseTLVReportConstants.TAG_SHIFT];
			report7DHeader.deviceID = StringUtilities.Byte2HexString(report, payloadIndex, Constants.DEVICE_ID_LEN);
			if (deviceType.equalsIgnoreCase(Constants.DEVICE_TYPES[Constants.CDMA])) {

				report7DHeader.deviceID = report7DHeader.deviceID.substring(2);

			} else {
				report7DHeader.deviceID = report7DHeader.deviceID.substring(1);
			}
			payloadIndex += Constants.DEVICE_ID_LEN;

			report7DHeader.sequenceNumber = Numbers.parseInt(report, payloadIndex,
					ParseTLVReportConstants.SEQUENCE_NUM_LENGTH, ByteOrder.BIG_ENDIAN);
			payloadIndex += ParseTLVReportConstants.SEQUENCE_NUM_LENGTH;

			report7DHeader.rawReport = StringUtilities.Byte2HexString(receivedUDPPacket.getPacket());
		} catch (Exception e) {
			System.out.println("Parse7DStartOfFrameHeader: Report header Exception: Device " + " payloadIndex: "
					+ payloadIndex + " reportLength: " + report.length + " at: " + report7DHeader.timestampReceived
					+ " " + e.getMessage());
			return null;
		}
		return report7DHeader;
	}

	public int Parse7DStartOfFrameHeader(byte[] report) {

		TimeUtilities.DateTimeInfo RTCDateTimeInfo = null;
		java.sql.Timestamp timestampReceived;
		int frameLengthFromReport;
		int completeFrameLength;
		String deviceTag;
		boolean acknowledge;
		String deviceID;
		String rawReport = StringUtilities.Byte2HexString(report);
		int sequenceNumber = Constants.MAX_INT_NUMBER_VALUE;

		int payloadIndex = 1;
		int fieldLen;
		try {
			byte b;

			byte tag = report[payloadIndex];

			acknowledge = (tag & ParseTLVReportConstants.ACK_MASK) != 0;
			payloadIndex++;

			b = report[payloadIndex];
			deviceTag = Constants.DEVICE_TYPES[b >>> ParseTLVReportConstants.TAG_SHIFT];
			deviceID = StringUtilities.Byte2HexString(report, payloadIndex, Constants.DEVICE_ID_LEN);
			if (deviceTag.equalsIgnoreCase(Constants.DEVICE_TYPES[Constants.CDMA])) {

				deviceID = deviceID.substring(2);

			} else {
				deviceID = deviceID.substring(1);
			}
			payloadIndex += Constants.DEVICE_ID_LEN;

			sequenceNumber = Numbers.parseInt(report, payloadIndex, ParseTLVReportConstants.SEQUENCE_NUM_LENGTH,
					ByteOrder.BIG_ENDIAN);
			payloadIndex += ParseTLVReportConstants.SEQUENCE_NUM_LENGTH;

			RTCDateTimeInfo = parseTLVDateTime(report, payloadIndex);

			payloadIndex += ParseTLVReportConstants.UTC_TIMESTAMP_LENGTH;
		} catch (Exception e) {

			payloadIndex = Constants.GENERAL_ERROR;
			return payloadIndex;
		}

		byte[] fieldBytes = GetOneOrTwoBytes(report, payloadIndex);
		payloadIndex = parseEvent(fieldBytes, payloadIndex);

		fieldBytes = GetOneOrTwoBytes(report, payloadIndex);
		fieldLen = fieldBytes.length;

		if (fieldLen > 0) {
			frameLengthFromReport = Numbers.parseInt(fieldBytes, 0, fieldBytes.length, ByteOrder.BIG_ENDIAN);
			payloadIndex += fieldLen;

			completeFrameLength = frameLengthFromReport + payloadIndex;
		} else {
			frameLengthFromReport = 0;
			completeFrameLength = rawReport.length();

			payloadIndex = Constants.GENERAL_ERROR;
		}

		return payloadIndex;

	}

	public static ReportHeader Parse7DFrameHeader(ReceivedUDPPacket receivedUDPPacket, String packetDestinationIP) {

		ReportHeader report7DHeader = new ReportHeader();

		report7DHeader.timestampReceived = receivedUDPPacket.getTimeStamp();
		InetAddress deviceIPAddress = receivedUDPPacket.getDeviceIPAddress();
		if (deviceIPAddress != null) {
			if (deviceIPAddress.getHostAddress() != null) {
				report7DHeader.deviceIPAddress = deviceIPAddress.getHostAddress();
			}
		}
		report7DHeader.devicePort = receivedUDPPacket.getDevicePort();
		report7DHeader.serverIPAddress = packetDestinationIP;
		report7DHeader.serverPort = receivedUDPPacket.getListenerPort();
		report7DHeader.rawReport = StringUtilities.Byte2HexString(receivedUDPPacket.getPacket());

		TimeUtilities.DateTimeInfo RTCDateTimeInfo = null;
		java.sql.Timestamp timestampReceived;
		int frameLengthFromReport;
		int completeFrameLength;
		String deviceTag;
		boolean acknowledge;
		String deviceID;
		byte[] report = receivedUDPPacket.getPacket();
		String rawReport = StringUtilities.Byte2HexString(report);
		int sequenceNumber = Constants.MAX_INT_NUMBER_VALUE;

		int payloadIndex = 1;
		int fieldLen;
		try {
			byte b;

			byte tag = report[payloadIndex];

			acknowledge = (tag & ParseTLVReportConstants.ACK_MASK) != 0;
			report7DHeader.acknowledge = acknowledge;
			payloadIndex++;

			b = report[payloadIndex];
			deviceTag = Constants.DEVICE_TYPES[b >>> ParseTLVReportConstants.TAG_SHIFT];
			deviceID = StringUtilities.Byte2HexString(report, payloadIndex, Constants.DEVICE_ID_LEN);
			if (deviceTag.equalsIgnoreCase(Constants.DEVICE_TYPES[Constants.CDMA])) {

				deviceID = deviceID.substring(2);

			} else {
				deviceID = deviceID.substring(1);
			}

			report7DHeader.deviceID = deviceID;
			payloadIndex += Constants.DEVICE_ID_LEN;

			sequenceNumber = Numbers.parseInt(report, payloadIndex, ParseTLVReportConstants.SEQUENCE_NUM_LENGTH,
					ByteOrder.BIG_ENDIAN);
			report7DHeader.sequenceNumber = sequenceNumber;
			payloadIndex += ParseTLVReportConstants.SEQUENCE_NUM_LENGTH;

			RTCDateTimeInfo = parseTLVDateTime(report, payloadIndex);

			payloadIndex += ParseTLVReportConstants.UTC_TIMESTAMP_LENGTH;
		} catch (Exception e) {

			payloadIndex = Constants.GENERAL_ERROR;
			return report7DHeader;
		}

		byte[] fieldBytes = GetOneOrTwoBytes(report, payloadIndex);
//		payloadIndex = parseEvent(fieldBytes, payloadIndex);
		report7DHeader.eventID = parseEvent(fieldBytes, payloadIndex);

		fieldBytes = GetOneOrTwoBytes(report, payloadIndex);
		fieldLen = fieldBytes.length;

		if (fieldLen > 0) {
			frameLengthFromReport = Numbers.parseInt(fieldBytes, 0, fieldBytes.length, ByteOrder.BIG_ENDIAN);
			payloadIndex += fieldLen;

			completeFrameLength = frameLengthFromReport + payloadIndex;
		} else {
			frameLengthFromReport = 0;
			completeFrameLength = rawReport.length();

			payloadIndex = Constants.GENERAL_ERROR;
		}

		return report7DHeader;

	}

	private static int parseEvent(byte[] fieldBytes, int payloadIndex) {

		String eventType = Constants.NO_STRING_VALUE;
		int eventTypeIndex;
		if ((fieldBytes == null) || (fieldBytes.length == 0)) {
			eventType = "ERROR";
			return payloadIndex;
		}

		int index = 0;
		int fieldLen = fieldBytes.length;

		if (fieldLen == 1) {
			eventTypeIndex = (int) fieldBytes[0] & 0xFF;
		} else {
			eventTypeIndex = Numbers.parseInt(fieldBytes, 0, fieldLen, ByteOrder.BIG_ENDIAN);
		}

		eventType = StringUtilities.Byte2HexString(fieldBytes);
//		System.out.println("*** "+eventType);
		if ((eventTypeIndex >= 0) && (eventTypeIndex < EVENT_TYPES.length)) {
			eventType = EVENT_TYPES[eventTypeIndex];
//			System.out.println("*+++++++++* "+eventType);

			// check if event needs refreshing from the DB.
			try {
				index = Integer.parseInt(eventType, 16);
//				System.out.println(index);
//				if (index == eventTypeIndex) {
//					// DeviceEvent de = EventDBWorker.GetEventByIDFromDB(eventTypeIndex, 1,
//					// databaseConnection);
//					DeviceEvent[] de = EventDBWorker.GetEventByIDFromDB(eventTypeIndex, dataSource, true, 1);
//					if ((de != null) && (de.length > 0) && (de[0] != null)) {
//						EVENT_TYPES[eventTypeIndex] = de[0].getEventType();
//						System.out.println("New event: " + index + " fetched from DB: " + de[0].getEventType());
//					} else {
//						System.out.println("New event: " + index + " not found in DB");
//					}
//				}
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
		return index;
	}

	public static TimeUtilities.DateTimeInfo parseTLVDateTime(byte[] report, int payloadIndex) {

		long timestamp = Numbers.parseLong(report, payloadIndex, ParseTLVReportConstants.UTC_TIMESTAMP_LENGTH,
				ByteOrder.BIG_ENDIAN);

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

	public static byte[] GetOneOrTwoBytes(byte[] report, int payloadIndex) {

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

	public static String[] EVENT_TYPES = new String[0xFFFF];

	static {

		for (int i = 0; i < EVENT_TYPES.length; i++) {
			EVENT_TYPES[i] = Integer.toHexString(i);
		}
	}

}
