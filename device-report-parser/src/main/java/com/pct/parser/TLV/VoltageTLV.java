package com.pct.parser.TLV;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.pct.parser.model.Voltage;
import com.pct.parser.utils.Numbers;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class VoltageTLV extends BaseTLV<Voltage> {

	@Autowired
	Voltage voltage;

	public static final int VOLTAGE_LEN = 2;

	@Override
	public Voltage parseSensor() {

		byte selector = report[payloadIndex];
		payloadIndex++;
		if (selector == 1) {
			voltage.TLVNumVoltages = 4;
			voltage.mainPower = Numbers.parseInt(report, payloadIndex, VOLTAGE_LEN, this.byteOrder);
			payloadIndex += VOLTAGE_LEN;

			voltage.auxPower = Numbers.parseInt(report, payloadIndex, VOLTAGE_LEN, this.byteOrder);
			payloadIndex += VOLTAGE_LEN;

			voltage.chargePower = Numbers.parseInt(report, payloadIndex, VOLTAGE_LEN, this.byteOrder);
			payloadIndex += VOLTAGE_LEN;

			voltage.batteryPower = Numbers.parseInt(report, payloadIndex, VOLTAGE_LEN, this.byteOrder);
		} else {
			voltage.TLVNumVoltages = 2;
			voltage.mainPower = Numbers.parseInt(report, payloadIndex, VOLTAGE_LEN, this.byteOrder);
			payloadIndex += VOLTAGE_LEN;

			voltage.batteryPower = Numbers.parseInt(report, payloadIndex, VOLTAGE_LEN, this.byteOrder);
		}
		return voltage;

	}

	@Override
	public void toJson() {
		if (voltage != null) {
			Gson gson = new Gson();
			String jsonString = gson.toJson(voltage);
			System.out.println(jsonString);
		}
	}
}
