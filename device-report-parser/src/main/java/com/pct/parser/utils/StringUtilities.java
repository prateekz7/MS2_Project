package com.pct.parser.utils;

import java.nio.CharBuffer;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import static java.nio.CharBuffer.wrap;
import static java.util.Objects.requireNonNull;

/**
 * Created by Sarit
 */
public class StringUtilities {

	public static final byte CR = 0x0D;
	public static final byte LF = 0x0A;
	public static final String ERROR_RED = "ERROR"; // "#F75E5E";
	public static final String OK = "OK";
	public static final String WARNING_YELLOW = "WARNING"; // "#FFFF82";
	public static final String OK_GREEN = "OK";
	public static final String NA_GREY = "--"; // "#333333";
	public static final String STRING_FIELD_SEPARATOR = ";";
	public static final String YES = "YES";
	public static final String NO = "NO";
	public static final String ERROR = "ERR";
	public static final String WARNING = "WARN";
	public static final String UNKNOWN = "UNKNOWN";
	public static final String NA = "NA";
	public static final String UNDEFINED = "Undefined";

	public static final String FORWARD_SLASH_REPLACEMENT = "-fslsh-";
	public static final String COLON_REPLACEMENT = "-cln-";
	public static final String QUESTION_MARK_REPLACEMENT = "-qmrk-";
	public static final String ASTERIX_REPLACEMENT = "-astrx-";

	/**
	 * Convert Hex string to Byte array
	 * 
	 * @param str - String for source
	 * @return - byte[] for result
	 */
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

	/**
	 * Convert byte array to hex string
	 * 
	 * @param b - array of bytes for source
	 * @return - String for result
	 */
	public static String Byte2HexString(byte[] b) {
		return Byte2HexString(b, 0, b.length);
	}

	/**
	 * Convert section of a byte array into hex string
	 * 
	 * @param b      - array of bytes for source
	 * @param offset - int for offset into the array
	 * @param len    - int for length of the array section
	 * @return - String for result
	 */
	public static String Byte2HexString(byte[] b, int offset, int len) {

		// String Buffer can be used instead

		String hexString = "";
		String hexDigits = "";

		for (int n = offset; n < (offset + len); n++) {
			hexDigits = (Integer.toHexString(b[n] & 0XFF));

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

	/**
	 * Thansform an array of ASCII bytes to a string. the byte array should contains
	 * only values in [0, 127].
	 *
	 * @param bytes  The byte array to transform
	 * @param offset int for start index into the byte array
	 * @param len    int for how many bytes to retrieve
	 * @return The resulting string
	 */
	public static String ASCIIBytesToString(byte[] bytes, int offset, int len) {
		if ((bytes == null) || (bytes.length == 0)) {
			return "";
		}

		char[] result = new char[len];

		offset = Math.max(0, offset);
		int endIndex = offset + len - 1;
		endIndex = Math.min(bytes.length - 1, endIndex);
		for (int i = offset, j = 0; i <= endIndex; i++, j++) {
			result[j] = (char) bytes[i];
		}

		return new String(result);
	}

	public static String hexToAscii(String hexStr) {
		StringBuilder output = new StringBuilder("");

		for (int i = 0; i < hexStr.length(); i += 2) {
			String str = hexStr.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}

		return output.toString();
	}

//    public static class AlphanumericComparator implements Comparator<CharSequence> {
//
//        private final Collator collator;
//
//        /**
//         * Creates a comparator that will use lexicographical sorting of the non-numerical parts of the compared strings.
//         */
//        public AlphanumericComparator() {
//            collator = null;
//        }
//
//        /**
//         * Creates a comparator that will use locale-sensitive sorting of the non-numerical parts of the compared strings.
//         *
//         * @param locale
//         *         the locale to use
//         */
//        public AlphanumericComparator(final Locale locale) {
//            this(Collator.getInstance(requireNonNull(locale)));
//        }
//
//        /**
//         * Creates a comparator that will use the given collator to sort the non-numerical parts of the compared strings.
//         *
//         * @param collator
//         *         the collator to use
//         */
//        public AlphanumericComparator(final Collator collator) {
//            this.collator = requireNonNull(collator);
//        }
//
//        @Override
//        public int compare(final CharSequence s1, final CharSequence s2) {
//            final CharBuffer b1 = wrap(s1);
//            final CharBuffer b2 = wrap(s2);
//
//            while (b1.hasRemaining() && b2.hasRemaining()) {
//                moveWindow(b1);
//                moveWindow(b2);
//
//                final int result = compare(b1, b2);
//                if (result != 0) {
//                    return result;
//                }
//
//                prepareForNextIteration(b1);
//                prepareForNextIteration(b2);
//            }
//
//            return s1.length() - s2.length();
//        }
//
//        private void moveWindow(final CharBuffer buffer) {
//            int start = buffer.position();
//            int end = buffer.position();
//            final boolean isNumerical = isDigit(buffer.get(start));
//            while (end < buffer.limit() && isNumerical == isDigit(buffer.get(end))) {
//                ++end;
//                if (isNumerical && (start + 1 < buffer.limit()) && isZero(buffer.get(start)) && isDigit(buffer.get(end))) {
//                    ++start; // trim leading zeros
//                }
//            }
//
//            buffer.position(start)
//                    .limit(end);
//        }
//
//        private int compare(final CharBuffer b1, final CharBuffer b2) {
//            if (isNumerical(b1) && isNumerical(b2)) {
//                return compareNumerically(b1, b2);
//            }
//
//            return compareAsStrings(b1, b2);
//        }
//
//        private boolean isNumerical(final CharBuffer buffer) {
//            return isDigit(buffer.charAt(0));
//        }
//
//        private boolean isDigit(final char c) {
//            if (collator == null) {
//                final int intValue = (int) c;
//                return intValue >= 48 && intValue <= 57;
//            }
//            return Character.isDigit(c);
//        }
//
//        private int compareNumerically(final CharBuffer b1, final CharBuffer b2) {
//            final int diff = b1.length() - b2.length();
//            if (diff != 0) {
//                return diff;
//            }
//            for (int i = 0; i < b1.remaining() && i < b2.remaining(); ++i) {
//                final int result = Character.compare(b1.charAt(i), b2.charAt(i));
//                if (result != 0) {
//                    return result;
//                }
//            }
//            return 0;
//        }
//
//        private void prepareForNextIteration(final CharBuffer buffer) {
//            buffer.position(buffer.limit())
//                    .limit(buffer.capacity());
//        }
//
//        private int compareAsStrings(final CharBuffer b1, final CharBuffer b2) {
//            if (collator != null) {
//                return collator.compare(b1.toString(), b2.toString());
//            }
//            return b1.toString().compareTo(b2.toString());
//        }
//
//        private boolean isZero(final char c) {
//            return c == '0';
//        }
//
//    }

	public static class AlphanumericComparator implements Comparator {
		/**
		 * The compare method that compares the alphanumeric strings
		 */
		public int compare(Object firstObjToCompare, Object secondObjToCompare) {
			String firstString = firstObjToCompare.toString();
			String secondString = secondObjToCompare.toString();

			if (secondString == null || firstString == null) {
				return 0;
			}

			int lengthFirstStr = firstString.length();
			int lengthSecondStr = secondString.length();

			int index1 = 0;
			int index2 = 0;

			while (index1 < lengthFirstStr && index2 < lengthSecondStr) {
				char ch1 = firstString.charAt(index1);
				char ch2 = secondString.charAt(index2);

				char[] space1 = new char[lengthFirstStr];
				char[] space2 = new char[lengthSecondStr];

				int loc1 = 0;
				int loc2 = 0;

				do {
					space1[loc1++] = ch1;
					index1++;

					if (index1 < lengthFirstStr) {
						ch1 = firstString.charAt(index1);
					} else {
						break;
					}
				} while (Character.isDigit(ch1) == Character.isDigit(space1[0]));

				do {
					space2[loc2++] = ch2;
					index2++;

					if (index2 < lengthSecondStr) {
						ch2 = secondString.charAt(index2);
					} else {
						break;
					}
				} while (Character.isDigit(ch2) == Character.isDigit(space2[0]));

				String str1 = new String(space1);
				String str2 = new String(space2);

				int result;

				if (Character.isDigit(space1[0]) && Character.isDigit(space2[0])) {
					Integer firstNumberToCompare = Integer.parseInt(str1.trim());
					Integer secondNumberToCompare = Integer.parseInt(str2.trim());
					result = firstNumberToCompare.compareTo(secondNumberToCompare);
				} else {
					result = str1.compareTo(str2);
				}

				if (result != 0) {
					return result;
				}
			}
			return lengthFirstStr - lengthSecondStr;
		}
	}
}
