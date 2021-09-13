package com.pct.reciever.services;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pct.reciever.engine.RuleEngine;
import com.pct.reciever.models.ReceivedUDPPacket;
import com.pct.reciever.models.ReportHeader;
import com.pct.reciever.utils.Constants;
import com.pct.reciever.utils.GeneralUtilities;

@Service
public class DatapacketNavigation {

	@Value("${listener.udp.ip}")
	private String url;
	@Value("${listener.udp.port}")
	private int port;
	private InetAddress deviceIPAddress;
	private int devicePort;
	private int listenerPort;
	private Inet4Address listenerIP;
//	private int localPort;
	public static final int LISTEN_PACKET_LEN = 2048;
	@Autowired
	private RuleEngine ruleEngine;
	private boolean isRunning = true;

	public void reportTypeCheck(DatagramPacket dataPacket, String uuid) {

		try {
			// open a soccket here to receive datagram packet from AT command processor,
			// change IP and Port of
			// datagram pkt and send to device

			listenerIP = (Inet4Address) Inet4Address.getByName(url);
			listenerPort = port;
			deviceIPAddress = dataPacket.getAddress();
			devicePort = dataPacket.getPort();
			String packetDestinationIP = GeneralUtilities.GetOutboundAddress(dataPacket.getSocketAddress())
					.getHostAddress();
			Instant instant = Instant.now();

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnnnnnnn")
					.withZone(ZoneId.systemDefault());
			;
			String now = dtf.format(instant);

			byte[] receivedData = dataPacket.getData();

			int packetLength = dataPacket.getLength();

			byte[] receivedDataPacket = new byte[packetLength];

			if (packetLength > 0) {

				System.arraycopy(receivedData, 0, receivedDataPacket, 0, packetLength);

				ReceivedUDPPacket receivedUDPPacket = null;

				try {

					receivedUDPPacket = new ReceivedUDPPacket(receivedDataPacket, deviceIPAddress, devicePort,
							listenerIP, listenerPort, uuid, instant);

					System.out.println(receivedDataPacket[0]);
					if ((receivedDataPacket[0] == Constants.UDP_RESPONSE_IDENTIFIER_BYTE)
							|| ((receivedDataPacket[0] == Constants.UDP_OLD_RESPONSE_IDENTIFIER_BYTES[0])
									&& (receivedDataPacket[1] == Constants.UDP_OLD_RESPONSE_IDENTIFIER_BYTES[1]))) {
						// this is not a device report but a device response to an AT command

						System.out.println("UDP response received");

					} else if (((receivedDataPacket[0] == Constants.UDP_COMMAND_PREFIX_A)
							|| (receivedDataPacket[0] == Constants.UDP_COMMAND_PREFIX_a))
							&& ((receivedDataPacket[1] == Constants.UDP_COMMAND_PREFIX_B)
									|| (receivedDataPacket[1] == Constants.UDP_COMMAND_PREFIX_b))
							&& (receivedDataPacket[2] == Constants.UDP_COMMAND_PREFIX_C)) {
						// this is an AT command to be sent to the device
						// create AT command obj and send to Device

						System.out.println("Recieved AT command");

						DatagramPacket datagramPacket = new DatagramPacket(receivedDataPacket,
								receivedDataPacket.length);

					} else if ((receivedDataPacket[0] == Constants.FRAME_CODE_7D)
							|| (receivedDataPacket[0] == Constants.FRAME_CODE_7E)) {

						System.out.println("7d report found..");
						ReportHeader reportHeader = Tokeniser.Parse7DFrameHeader(receivedUDPPacket,
								packetDestinationIP);
						ruleEngine.executeRules(reportHeader, receivedUDPPacket);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("error occured");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
