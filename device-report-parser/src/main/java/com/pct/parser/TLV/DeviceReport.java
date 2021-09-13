package com.pct.parser.TLV;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pct.parser.model.BLEDoorSensor;
import com.pct.parser.model.Voltage;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DeviceReport {

	@Autowired
	public BaseTLV<BLEDoorSensor> bleDoorSensor;

	@Autowired
	public BaseTLV<Voltage> voltage;

	public DeviceReport() {

	}
}
