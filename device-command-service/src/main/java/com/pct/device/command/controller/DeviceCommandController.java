package com.pct.device.command.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.device.command.dto.MessageDTO;
import com.pct.device.command.payload.DeviceCommandRequest;
import com.pct.device.command.service.IDeviceCommandService;

@RestController
@RequestMapping("/device-command")
public class DeviceCommandController {

	private static final Logger logger = LoggerFactory.getLogger(DeviceCommandController.class);

	@Autowired
	private IDeviceCommandService deviceCommandSerivce;

	@PostMapping()
	public ResponseEntity<MessageDTO<String>> addDeviceCommand(
			@Validated @RequestBody DeviceCommandRequest deviceCommandRequest, HttpServletRequest httpServletRequest) {
		logger.info("Inside addDeviceCommand Api Controller");
		try {
			deviceCommandSerivce.saveDeviceCommand(deviceCommandRequest);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return new ResponseEntity<MessageDTO<String>>(new MessageDTO<String>("Command added successfully", "", true),
				HttpStatus.CREATED);

	}

	@GetMapping()
	public ResponseEntity<MessageDTO<String>> getDeviceCommandFromRedis(@RequestParam("deviceId") String deviceID,
			HttpServletRequest httpServletRequest) {
		logger.info("Inside addDeviceCommand Api Controller");
		try {
			deviceCommandSerivce.getDeviceCommandFromRedis(deviceID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<MessageDTO<String>>(new MessageDTO<String>("Command added successfully", "", true),
				HttpStatus.CREATED);

	}

}
