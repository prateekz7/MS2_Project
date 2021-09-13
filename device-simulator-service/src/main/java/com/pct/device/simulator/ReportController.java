package com.pct.device.simulator;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.device.simulator.util.StringUtilities;

@RestController
@RequestMapping("/report")
public class ReportController {

	Logger logger = LoggerFactory.getLogger(ReportController.class);

	@Value("${listener.ip}")
	private String listenerIp;
	@Value("${listener.port}")
	private int listenerPort;

	@Value("${simulator.server.ip}")
	private String serverIp;
	@Value("${simulator.server.port}")
	private int serverPort;

	@GetMapping("/sendReport")
	public void sendRawReport(@RequestParam(value = "rawreport", required = true) String rawReport) {
		logger.info("Inside  Method");
		try {

			logger.info("packet recieved on Simultor");
			DatagramSocket socket = SocketObject.getSocket(serverIp, serverPort);
			byte[] buf = StringUtilities.Hex2Byte(rawReport);
			InetAddress inetAddress = InetAddress.getByName(listenerIp);
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, inetAddress, listenerPort);
			socket.send(datagramPacket);
			logger.info("Datagram packet sent to UDP Listener");
		} catch (Exception e) {
			throw new RuntimeException("Failed while sending raw report");
		}

	}

}
