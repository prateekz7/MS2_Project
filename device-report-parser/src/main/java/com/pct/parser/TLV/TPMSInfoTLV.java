package com.pct.parser.TLV;

import java.nio.ByteOrder;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.pct.parser.model.TPMSAlpha;
import com.pct.parser.model.TPMSAlphaMeasure;
import com.pct.parser.utils.Numbers;
import com.pct.parser.utils.StringUtilities;

public class TPMSInfoTLV extends BaseTLV<TPMSAlpha> {

	byte[] tpms;
	boolean isParsed = false;
	public final int ALPHA_TPMS_TEMPERATURE_C_OFFSET = 50;
	public final int ALPHA_TPMS_TEMPERATURE_C_NA_1 = -50;
	public final int ALPHA_TPMS_TEMPERATURE_C_NA_2 = 205;
	public final String[] STATUS_STRS = new String[] { SENSOR_HEALTHY, "Faulty" };
	public final String[] NO_TIRES_TITLES = new String[] { "ATIS", "Tank" };

	public static final float MILLIBAR_TO_PSI_FACTOR = 0.0145037738f;
	public transient ByteOrder transByteOrder = ByteOrder.BIG_ENDIAN; // don't serialize this
	public static String SENSOR_HEALTHY = "Healthy";
	public static String SENSOR_UNRESPONSIVE = "Unresponsive";
	public static String SENSOR_COMM_ERROR = "Comm error";
	public static String SENSOR_MISSING_SENSORS = "Missing sensors";
	public static String SENSOR_FAULT = "Fault";
	public static String SENSOR_UNKNOWN = "Unknown";

	TPMSAlpha tPMSAlpha = new TPMSAlpha();
	ArrayList<TPMSAlphaMeasure> tPMSAlphaMeasures = new ArrayList<TPMSAlphaMeasure>();

	@Override
	public TPMSAlpha parseSensor() {
		tpms = new byte[valueLength];
		System.arraycopy(report, payloadIndex, tpms, 0, valueLength);

		if (tpms != null && tpms.length > 0) {

			int temp = tpms[0] & 0xFF;
			if (temp == 0x80) {
				tPMSAlpha.status = SENSOR_UNRESPONSIVE;
			} else if (temp == 0x00) {
				tPMSAlpha.status = SENSOR_HEALTHY;
			} else if (temp == 0x01) {
				tPMSAlpha.status = SENSOR_FAULT;
			} else if (temp == 0x40) {
				tPMSAlpha.status = SENSOR_COMM_ERROR;
			} else if (temp == 0x20) {
				tPMSAlpha.status = SENSOR_MISSING_SENSORS;
			} else {
				tPMSAlpha.status = SENSOR_UNKNOWN;
			}

			if (tpms.length > 1) {
				tPMSAlpha.numSensors = tpms[1] & 0xFF;

				try {
					int j = 2;

					byte mask = 0b00000011;
					for (int i = 0; ((i < tPMSAlpha.numSensors) && (j < tpms.length)); i++) {
						TPMSAlphaMeasure tpmsMeasure = new TPMSAlphaMeasure();
						tpmsMeasure.locationCodeHexStr = StringUtilities.Byte2HexString(new byte[] { tpms[j] });
						tpmsMeasure.axleTireireLocationCode = (int) Numbers.parseShort(tpms, j, 1, this.transByteOrder);

						switch (tpmsMeasure.axleTireireLocationCode) {

						case 0x21:
							tpmsMeasure.tireLocationStr = "FLO";
							tpmsMeasure.axleLocation = 1;
							tpmsMeasure.tireLocation = 1;
							break;

						case 0x24:
							tpmsMeasure.tireLocationStr = "FRO";
							tpmsMeasure.axleLocation = 1;
							tpmsMeasure.tireLocation = 2;
							break;

						case 0x22:
							tpmsMeasure.tireLocationStr = "FLI";
							tpmsMeasure.axleLocation = 1;
							tpmsMeasure.tireLocation = 3;
							break;

						case 0x23:
							tpmsMeasure.tireLocationStr = "FRI";
							tpmsMeasure.axleLocation = 1;
							tpmsMeasure.tireLocation = 4;
							break;

						case 0x26:
							tpmsMeasure.tireLocationStr = "RLI";
							tpmsMeasure.axleLocation = 2;
							tpmsMeasure.tireLocation = 3;
							break;

						case 0x27:
							tpmsMeasure.tireLocationStr = "RRI";
							tpmsMeasure.axleLocation = 2;
							tpmsMeasure.tireLocation = 4;
							break;

						case 0x25:
							tpmsMeasure.tireLocationStr = "RLO";
							tpmsMeasure.axleLocation = 2;
							tpmsMeasure.tireLocation = 1;
							break;

						case 0x28:
							tpmsMeasure.tireLocationStr = "RRO";
							tpmsMeasure.axleLocation = 2;
							tpmsMeasure.tireLocation = 2;
							break;

						case 0x29:
							tpmsMeasure.tireLocationStr = "Tank";
							break;

						case 0x2a:
							tpmsMeasure.tireLocationStr = "ATIS";
							break;
						case 0x41:
							tpmsMeasure.tireLocationStr = "FL";
							break;
						case 0x42:
							tpmsMeasure.tireLocationStr = "FR";
							break;
						case 0x43:
							tpmsMeasure.tireLocationStr = "RL";
							break;
						case 0x44:
							tpmsMeasure.tireLocationStr = "RR";
							break;
						case 0x49:
							tpmsMeasure.tireLocationStr = "Tank";
							break;
						default:
							tpmsMeasure.tireLocationStr = StringUtilities.UNDEFINED;
							break;
						}
						j++;
						tpmsMeasure.tirePressureMBAR = Numbers.parseInt(tpms, j, 2, this.transByteOrder);
						tpmsMeasure.tirePressurePSI = tpmsMeasure.tirePressureMBAR * MILLIBAR_TO_PSI_FACTOR;

						j += 2;
						int tempC = Numbers.parseInt(tpms, j, 1, this.transByteOrder) - ALPHA_TPMS_TEMPERATURE_C_OFFSET;
						tpmsMeasure.tireTemperatureC = (float) tempC;
						j += 1;
						tPMSAlphaMeasures.add(tpmsMeasure);
					}

					isParsed = true;
				} catch (ArrayIndexOutOfBoundsException e) {
					e.getStackTrace();
				}
			}
		}
		tPMSAlpha.tPMSmeasures = tPMSAlphaMeasures;
		return tPMSAlpha;
	}

	@Override
	public void toJson() {
		if (tPMSAlpha != null) {
			Gson gson = new Gson();
			String jsonString = gson.toJson(tPMSAlpha);
			System.out.println(jsonString);
		}
	}
}
