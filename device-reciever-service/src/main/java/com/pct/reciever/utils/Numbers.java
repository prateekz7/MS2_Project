package com.pct.reciever.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;

public class Numbers {

	public static final int SHORT_SIZE = Short.SIZE / 8;
	public static final int INT_SIZE = Integer.SIZE / 8;
	public static final int LONG_SIZE = Long.SIZE / 8;
	public static final int ERROR_INDEX = 0;
	public static final float METER_TO_FEET_MULTIPLIER = 3.28084f;
	public static final float SPEED_MPH_TO_KMH_MULTIPLIER = 1.60934f;
	public static final float KILOMETER_TO_MILE_MULTIPLIER = 0.621371f;
	public static final float KILOMETER_10TH_TO_MILE_MULTIPLIER = 0.0621371f;
	public static final int TENTH_VOLT_TO_1MILLIVOLT_MULTIPLIER = 100;
	public static final int MILLIVOLT_TO_VOLT_FACTOR = 1000;
	public static final int TENTH_VOLT_TO_VOLT_FACTOR = 10;
	public static final float CELSIUS_TO_KELVIN_OFFSET = 273.15f;
	public static final float CELSIUS_TO_FAHRENHEIT_FACTOR = 1.8f;
	public static final float CELSIUS_TO_FAHRENHEIT_OFFSET = 32;
	public static DecimalFormat decimal6Format = new DecimalFormat("####.######");
	public static DecimalFormat decimalForced2Format = new DecimalFormat("#0.00");
	public static DecimalFormat decimal1Format = new DecimalFormat("#0.#");

	/**
	 * Convert array of bytes to an integer. For array of 2 bytes the result is
	 * signed. for 1 byte it is unsigned.
	 * 
	 * @param src        - byte array to be parsed
	 * @param startIndex - int for offset into the byte array
	 * @param length     - int for number of bytes to parse
	 * @param byteOrder  - ByteOrder object
	 * @return - iny with the parsed value or 0 if the input array is null/empty or
	 *         the input length is wrong
	 */
	public static int parseInt(byte[] src, int startIndex, int length, ByteOrder byteOrder) {
		return parseInt(src, startIndex, length, byteOrder, null);
	}

	/**
	 * Convert array of bytes to an integer. For array of 2 bytes the result is
	 * signed. for 1 byte it is unsigned.
	 * 
	 * @param src        - byte array to be parsed
	 * @param startIndex - int for offset into the byte array
	 * @param length     - int for number of bytes to parse
	 * @param byteOrder  - ByteOrder object
	 * @param error      - arry of boolean with allocation for at least one member
	 *                   for error return instead of using exception
	 * @return - iny with the parsed value or 0 if the input array is null/empty or
	 *         the input length is wrong
	 */
	public static int parseInt(byte[] src, int startIndex, int length, ByteOrder byteOrder, boolean[] error) {

		// Checks and restrictions
		//////////////////////////
		if ((src == null) || (src.length == 0) || (length > src.length) || (length > INT_SIZE)) {
			if ((error != null) && (error.length > 0)) {
				error[0] = true;
			}
			return 0;
		}

		// Now convert
		//////////////
		int result = 0;
		int shift = 0;
		if (byteOrder.equals(ByteOrder.BIG_ENDIAN)) {
			for (int i = (startIndex + length - 1); i >= startIndex; i--) {
				result |= ((src[i] & 0xff) << shift);
				shift += 8;
			}
		} else {
			for (int i = startIndex; i <= (startIndex + length - 1); i++) {
				result |= ((src[i] & 0xff) << shift);
				shift += 8;
			}
		}
		if ((error != null) && (error.length > 0)) {
			error[0] = false;
		}
		return result;
	}

	/**
	 * Convert array of bytes to a Long integer. For array of 2 bytes the result is
	 * signed. for 1 byte it is unsigned.
	 * 
	 * @param src        - byte array to be parsed
	 * @param startIndex - int for offset into the byte array
	 * @param length     - int for number of bytes to parse
	 * @param byteOrder  - ByteOrder object
	 * @return - long with the parsed value or 0 if the input array is null/empty or
	 *         the input length is wrong
	 */
	public static long parseLong(byte[] src, int startIndex, int length, ByteOrder byteOrder) {
		return parseInt(src, startIndex, length, byteOrder, null);
	}

	/**
	 * Convert array of bytes to a long integer. For array of 2 bytes the result is
	 * signed. for 1 byte it is unsigned.
	 * 
	 * @param src        - byte array to be parsed
	 * @param startIndex - int for offset into the byte array
	 * @param length     - int for number of bytes to parse
	 * @param byteOrder  - ByteOrder object
	 * @param error      - arry of boolean with allocation for at least one member
	 *                   for error return instead of using exception
	 * @return - short with the parsed value or 0 if the input array is null/empty
	 *         or the input length is wrong
	 */
	public static long parseLong(byte[] src, int startIndex, int length, ByteOrder byteOrder, boolean[] error) {

		// Checks and restrictions
		//////////////////////////
		if ((src == null) || (src.length == 0) || (length > src.length) || (length > LONG_SIZE)) {
			if ((error != null) && (error.length > 0)) {
				error[0] = true;
			}
			return 0;
		}

		// Now convert
		//////////////
		long result = 0;
		int shift = 0;
		if (byteOrder.equals(ByteOrder.BIG_ENDIAN)) {
			for (int i = (startIndex + length - 1); i >= startIndex; i--) {
				result |= ((long) (src[i] & 0xff) << shift); // must cast to long since the default when signed shifting
																// is int (4 bytes)
				shift += 8;
			}
		} else {
			for (int i = startIndex; i <= (startIndex + length - 1); i++) {
				result |= ((src[i] & 0xff) << shift);
				shift += 8;
			}
		}
		if ((error != null) && (error.length > 0)) {
			error[0] = false;
		}
		return result;
	}

	/**
	 * Convert array of bytes to an integer. For array of 2 bytes the result is
	 * signed. for 1 byte it is unsigned.
	 * 
	 * @param src        - byte array to be parsed
	 * @param startIndex - int for offset into the byte array
	 * @param length     - int for number of bytes to parse
	 * @param byteOrder  - ByteOrder object
	 * @return - short with the parsed value or 0 if the input array is null/empty
	 *         or the input length is wrong
	 */
	public static short parseShort(byte[] src, int startIndex, int length, ByteOrder byteOrder) {
		return parseShort(src, startIndex, length, byteOrder, null);
	}

	/**
	 * Convert array of bytes to a short integer. For array of 2 bytes the result is
	 * signed. for 1 byte it is unsigned.
	 * 
	 * @param src        - byte array to be parsed
	 * @param startIndex - int for offset into the byte array
	 * @param length     - int for number of bytes to parse
	 * @param byteOrder  - ByteOrder object
	 * @param error      - arry of boolean with allocation for at least one member
	 *                   for error return instead of using exception
	 * @return - short with the parsed value or 0 if the input array is null/empty
	 *         or the input length is wrong
	 */
	public static short parseShort(byte[] src, int startIndex, int length, ByteOrder byteOrder, boolean[] error) {

		// Checks and restrictions
		//////////////////////////
		if ((src == null) || (src.length == 0) || (length > src.length) || (length > SHORT_SIZE)) {
			if ((error != null) && (error.length > 0)) {
				error[0] = true;
			}
			return 0;
		}

		// Now convert
		//////////////
		short result = 0;
		int shift = 0;
		if (byteOrder.equals(ByteOrder.BIG_ENDIAN)) {
			for (int i = (startIndex + length - 1); i >= startIndex; i--) {
				result |= ((src[i] & 0xff) << shift);
				shift += 8;
			}
		} else {
			for (int i = startIndex; i <= (startIndex + length - 1); i++) {
				result |= ((src[i] & 0xff) << shift);
				shift += 8;
			}
		}
		if ((error != null) && (error.length > 0)) {
			error[0] = false;
		}
		return result;
	}

	public static int GetSignedShortFromUnsignedInt(int source) {

		int signedShort;

		ByteBuffer byteBuffer = ByteBuffer.allocate(4);
		// byteBuffer.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a
		// byte buffer is always BIG_ENDIAN.
		byteBuffer.putInt(source);

		byte[] byteArray = byteBuffer.array();

		signedShort = parseShort(byteArray, 2, 2, ByteOrder.BIG_ENDIAN);

		return signedShort;
	}

	public static double RoundUp(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static double RoundDown(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_DOWN);
		return bd.doubleValue();
	}

}
