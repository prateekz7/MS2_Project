package com.pct.parser.engine;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pct.parser.TLV.DeviceReport;
import com.pct.parser.TLV.ParseRawReport;
import com.pct.parser.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReportServiceImpl {

	@Autowired
	private ObjectFactory<ParseRawReport> parseRawReportObjectFactory;

	@Autowired
	private ObjectFactory<DeviceReport> deviceReportObjectFactory;

	public BaseResponse getParsedReport(String report) throws BeansException, IOException {
		log.info("Inside Service Class");
		// String report =
		// "7d01001511500645987817d8296562b1e6083e0117bc296562b1075f07c203b431bd3900003b51820000003de21009012e96001a00000ffff0010400fd00fde60809000000000000030000e628030c9e18";

		byte[] rawInput = hexStringToByteArray(report);
		DeviceReport deviceReport = deviceReportObjectFactory.getObject();
		parseRawReportObjectFactory.getObject().initilize(rawInput, java.nio.ByteOrder.BIG_ENDIAN, "", deviceReport);

		return BaseResponse.builder().bleDoorSensor(deviceReport.bleDoorSensor.parseSensor())
				.voltage(deviceReport.voltage.parseSensor()).build();
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
}
