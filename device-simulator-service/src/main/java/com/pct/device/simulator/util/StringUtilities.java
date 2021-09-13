package com.pct.device.simulator.util;

public class StringUtilities {
	public static byte[] Hex2Byte(String str) {
		return Hex2Byte(str, 0, str.length());
	}

	/**
	 * Convert Hex sub-string to byte array
	 * 
	 * @param str    - String for source
	 * @param offset - int for starting location of substring
	 * @param len    - int for length of substring
	 * @return - byte[] for result
	 */
	public static byte[] Hex2Byte(String str, int offset, int len) {
		byte[] bytes = new byte[len / 2];
		for (int i = offset, j = 0; i < (offset + bytes.length); i++, j++) {
			bytes[j] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}

	public static String Byte2HexString(byte[] b, int offset, int len) {

		// String Buffer can be used instead

		String hexString = "";
		String hexDigits = "";

		for (int n = offset; n < (offset + len); n++) {
			hexDigits = (java.lang.Integer.toHexString(b[n] & 0XFF));

			if (hexDigits.length() == 1) {
				hexString = hexString + "0" + hexDigits;
			} else {
				hexString = hexString + hexDigits;
			}

			if (n < (offset + len) - 1) {
				hexString = hexString + "";
			}
		}

		return hexString;
	}

}
