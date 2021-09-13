package com.pct.device.simulator;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.device.simulator.util.StringUtilities;

/**
 * @author Abhishek on 24/04/20
 */

@RestController
@RequestMapping("/report")
public class ReportController {

	Logger logger = LoggerFactory.getLogger(ReportController.class);

	String serverIP = "127.0.0.1";
	int serverPort = 15023;

	@GetMapping("/sendReport")
	public void sendRawReport(@RequestParam(value = "rawreport", required = true) String rawReport) {
		logger.info("Inside  Method");
		try {

			logger.info("packet recieved on device");
			DatagramSocket socket = SocketObject.getSocket();
			byte[] buf = StringUtilities.Hex2Byte(rawReport);
			InetAddress inetAddress = InetAddress.getByName(serverIP);
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, inetAddress, serverPort);
			socket.send(datagramPacket);
			logger.info("Datagram packet sent to UDP Listener");
		} catch (Exception e) {
			throw new RuntimeException("Failed while sending raw report");
		}

	}

}
