package com.pct.parser.TLV;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pct.parser.model.BLEDoorSensor;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BLEDoorSensorTLV extends BaseTLV<BLEDoorSensor> {
	BLEDoorSensor bleDoorSensorM;
	boolean isParsed = false;
	private byte[] bleDoorSensor = null;
	public final Map<Integer, String> C_STATUS = new HashMap<Integer, String>() {
		{
			put(0, "Initializing");
			put(4, "Offline");
			put(12, "Online");
			put(15, "Not Installed");
		}
	};
	public final String[] S_STATUS = new String[] { "", "Offline", "Online" };
	public final String[] STATE = new String[] { "Open", "Closed", "Unknown" };
	public final String[] TYPE = new String[] { "Roll", "Swing" };
	public final String[] EM = new String[] { "Missed", "Detected" };
	public final String[] BATTERY = new String[] { "Empty", "Low", "OK", "Unknown" };
	public static final float CELSIUS_TO_FAHRENHEIT_FACTOR = 1.8f;
	public static final float CELSIUS_TO_FAHRENHEIT_OFFSET = 32;

	@Override
	public BLEDoorSensor parseSensor() {
		System.out.println("I am in parse() of BLEDoorSensorTLV");
		bleDoorSensorM = new BLEDoorSensor();
		bleDoorSensor = new byte[valueLength];
		// System.arraycopy(report, payloadIndex, bleDoorSensor, 0, valueLength);
		if (bleDoorSensor != null && bleDoorSensor.length > 0) {
			int temp = bleDoorSensor[0];
			int selectorBits = (temp >>> 4) & 0xf;
			int statusBits = temp & 0xf;
			bleDoorSensorM.commStatus = C_STATUS.get(statusBits);
			if (bleDoorSensor.length > 1) {
				if (bleDoorSensorM.commStatus == "Online") {
					temp = bleDoorSensor[1];
					int sStatusBits = (temp >>> 6) & 3;
					bleDoorSensorM.sensorStatus = S_STATUS[sStatusBits];
					if (bleDoorSensorM.sensorStatus == "Online") {
						int dStateBits = (temp >>> 4) & 3;
						bleDoorSensorM.doorState = STATE[dStateBits];
						int typeBits = (temp >>> 3) & 1;
						bleDoorSensorM.doorType = TYPE[typeBits];
						int emBits = (temp >>> 2) & 1;
						bleDoorSensorM.doorSequence = EM[emBits];
						int batteryBits = temp & 3;
						bleDoorSensorM.battery = BATTERY[batteryBits];

						// temperature
						if (bleDoorSensor.length == 3) {
							temp = bleDoorSensor[2];
							if (temp > 126)
								bleDoorSensorM.temperatureC = 127;
							else if (temp < -127)
								bleDoorSensorM.temperatureC = -127;
							else
								bleDoorSensorM.temperatureC = temp;
							// bleDoorSensorM.temperatureF = CELSIUS_TO_FAHRENHEIT_FACTOR*
							// bleDoorSensorM.temperatureC + CELSIUS_TO_FAHRENHEIT_OFFSET;
						} else {
							bleDoorSensorM.temperatureC = null;

						}
					} else {
						bleDoorSensorM.sensorStatus = "NA";
						bleDoorSensorM.doorState = "NA";
						bleDoorSensorM.doorType = "NA";
						bleDoorSensorM.doorSequence = "NA";
						bleDoorSensorM.battery = "NA";
						bleDoorSensorM.temperatureC = null;
						// bleDoorSensorM.temperatureF = null;

					}
				}
			} else {
				bleDoorSensorM.sensorStatus = "NA";
				bleDoorSensorM.doorState = "NA";
				bleDoorSensorM.doorType = "NA";
				bleDoorSensorM.doorSequence = "NA";
				bleDoorSensorM.battery = "NA";
				bleDoorSensorM.temperatureC = null;
				// bleDoorSensorM.temperatureF = null;
			}
			isParsed = true;
		}

		toJson();
		return bleDoorSensorM;
	}

	@Override
	public void toJson() {
		System.out.println("I am in toJSON() of BLEDoorSensorTLV");
		if (bleDoorSensorM != null) {
			Gson gson = new Gson();
			String jsonString = gson.toJson(bleDoorSensorM);
			System.out.println(jsonString);
		}

	}
}
