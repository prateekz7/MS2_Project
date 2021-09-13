package com.pct.reciever.utils;

import java.io.Serializable;

public class Constants implements Serializable {

	public static final int LISTEN_PACKET_LEN = 2048;
	public static final int MAX_INT_NUMBER_VALUE = 1000000000;

	// Device response constants
	public static final byte UDP_RESPONSE_IDENTIFIER_BYTE = '%';
	public static final int UDP_RESPONSE_IDENTIFIER_LEN = 1;
	public static final byte UDP_RESPONSE_DELIMITER_BYTE = '^';
	public static final byte UDP_COMMAND_IP_DELIMITER_BYTE = '&';
	public static final int UDP_RESPONSE_DELIMITER_LEN = 1;
	public static final byte UDP_COMMAND_END_BYTE = '~';
	public static final int UDP_COMMAND_END_BYTE_LEN = 1;
	public static final byte[] UDP_OLD_RESPONSE_IDENTIFIER_BYTES = new byte[] { 0x0d, 0x0a }; // carriage return, line
																								// feed
	public static final String XINVER_REPONSE_DEVICE_ID_COLON_PREFIX = ":";
	public static final String XINVER_REPONSE_DEVICE_ID_r_POSTFIX = "\r";
	public static final int AT_COMMAND_PREFIX_OFFSET = 2;
	public static final String XINVER_RESPONSE_END = "OK";
	public static final String UNKNOWN_COMMAND = "unknown";
	public static final String OLD_COMMAND_POSTFIX_DELIMITER = "#";

	// Device report constants
	public static final int FRAME_CODE_7E = 0x7E;
	public static final int FRAME_CODE_7D = 0x7D;

	// UDP communication constants
	public static final byte UDP_COMMAND_PREFIX_A = 'A';
	public static final byte UDP_COMMAND_PREFIX_a = 'a';
	public static final byte UDP_COMMAND_PREFIX_B = 'T';
	public static final byte UDP_COMMAND_PREFIX_b = 't';
	public static final byte UDP_COMMAND_PREFIX_C = '+';

	// length constants are in bytes
	public static final int FRAME_CODE_INDEX = 0;
	public static final int FRAME_CODE_LEN = 1;
	public static final int LENGTH_INDEX = 1;
	public static final int LENGTH_LEN = 2;
	public static final int PROTOCOL_ID_INDEX = 3;
	public static final int PROTOCOL_ID_LEN = 1;
	public static final int DEVICE_TAG_INDEX = 4;
	public static final int DEVICE_TAG_LEN = 1;
	public static final int DEVICE_ID_INDEX = 5;
	public static final int DEVICE_ID_LEN = 8;
	public static final int PAYLOAD_INDEX = 13;

	public static final byte TAG_MASK = 0b00001111;
	public static final int TAG_SHIFT = 4;

	public static final int CDMA = 1;
	public static final String[] DEVICE_TYPES = new String[] { "GSM", "CDMA", null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, StringUtilities.UNDEFINED };

	// Talon fields
	// public static final byte TALON_TAG_MASK = 0b01000000;
	// public static final byte TALON_TAG_SHIFT = 6;
	public static final int ACKNOWLEDGE_INDEX = 1;
	public static final byte START_OF_FRAME_7E_AND_TLV = (byte) 0x7E;
	public static final byte START_OF_FRAME_TALON = (byte) 0xE0;
	public static final byte PAYLOAD_LENGTH = (byte) 2;
	public static final byte PROTOCOL_ID = (byte) 0xE0;
	public static final byte RESERVED = (byte) 0x00;
	public static final byte TAG = (byte) 0x0F;
	public static final int TAG_INDEX = 3;
	public static final int SEQUENCE_NUM_INDEX = 38;
	public static final short ODD_EVEN_FRAME_MASK = 0b01000000;
	public static final int TAG_INNDEX = 1;
	public static final int TAG_LEN = 1;
	public static final byte LENGTH_1_2_MASK = (byte) 0b11100000;
	public static final int SEQUENCE_NUM_LENGTH = 2;
	public static final int UTC_TIMESTAMP_LENGTH = 4;
	public static final int UTC_TIMESTAMP_YEAR_OFFSET = 2000;
	public static final byte ACK_MASK = 0b011;
	public static final int DIAGNOSTICS_LIGHT_LEN = 1;
	public static final int DIAGNOSTICS_CODES_LEN_LEN = 1;

	public static final String NO_STRING_VALUE = "NA";
	// error fields
	public static final int GENERAL_ERROR = -1;

}
