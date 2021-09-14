package com.pct.device.command.service.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.http.conn.util.InetAddressUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pct.device.command.entity.DeviceCommand;
import com.pct.device.command.payload.DeviceCommandRequest;
import com.pct.device.command.repository.IDeviceCommandRespository;
import com.pct.device.command.repository.RedisDeviceCommandRepository;
import com.pct.device.command.service.IDeviceCommandService;
import com.pct.device.command.util.Constant;
import com.pct.device.command.util.StringUtilities;

@Service
public class DeviceCommandServiceImpl implements IDeviceCommandService {
	
	@Value("${listener.udp.ip}")
	private String listenerIp;
	@Value("${listener.udp.port}")
	private int listenerPort;
	public static final byte START_OF_FRAME_7E_AND_TLV = (byte) 0x7E;
	public static final byte PAYLOAD_LENGTH = (byte) 2;
	public static final byte PROTOCOL_ID = (byte) 0xE0;
	public static final byte RESERVED = (byte) 0x00;
	public static final byte TAG = (byte) 0x0F;
	public static final int TAG_INDEX = 3;

	static Logger logger = LoggerFactory.getLogger(DeviceCommandServiceImpl.class);

	private static final String DEVICE_ID_PREFIX = "deviceID:";

	private DatagramSocket socket;

	@Autowired
	private IDeviceCommandRespository deviceCommandRepository;

	@Autowired
	private RedisDeviceCommandRepository redisDeviceCommandRepository;

	private static final List<String> FIELDS = new ArrayList() {
		{
			add("at_command");
			add("priority");
			add("source");
			add("device_id");

		}
	};

	@Override
	@Transactional
	public DeviceCommand saveDeviceCommand(DeviceCommandRequest deviceCommandRequest) {
		DeviceCommand deviceCommandManager = new DeviceCommand();
		logger.info("Device Command Reuest recieved on AT-command Processor");
		try {
			if (deviceCommandRequest.getAt_command() != null
					&& (!deviceCommandRequest.getAt_command().equalsIgnoreCase(""))) {
				deviceCommandManager.setAtCommand(deviceCommandRequest.getAt_command());
				deviceCommandManager.setDeviceId(deviceCommandRequest.getDevice_id());
				deviceCommandManager.setPriority(deviceCommandRequest.getPriority());
				deviceCommandManager.setLastExecuted(deviceCommandRequest.getLast_executed());
				deviceCommandManager.setSource(deviceCommandRequest.getSource());
				deviceCommandManager.set_success(deviceCommandRequest.isSuccess());
				deviceCommandManager.setCreatedDate(Instant.now());
				String Uuid = "";
				boolean isUuidUnique = false;
				while (!isUuidUnique) {
					Uuid = UUID.randomUUID().toString();
					DeviceCommand byUuid = deviceCommandRepository.findByUuid(Uuid);
					if (byUuid == null) {
						isUuidUnique = true;
					}
				}
				deviceCommandManager.setUuid(Uuid);
				deviceCommandManager = deviceCommandRepository.save(deviceCommandManager);
				Map<String, String> map = new HashMap<>();
				map.put("at_command", deviceCommandManager.getAtCommand());
				map.put("priority", deviceCommandManager.getPriority());
				map.put("source", deviceCommandManager.getSource());
				map.put("device_id", deviceCommandManager.getDeviceId());
				// map.put("is_success", deviceCommandManager.is_success());
				// redisDeviceCommandRepository.add(DEVICE_ID_PREFIX +
				// deviceCommandManager.getDeviceId(), map);
			}

			return deviceCommandManager;
		} catch (Exception e) {
			e.printStackTrace();
			return deviceCommandManager;
		}
	}

	public byte[] getAcknowledgePacket(boolean isCloseDeviceListenWindow, byte[] report, ByteOrder byteOrder) {

		byte[] packet = null;
		boolean acknowledge;
		byte tag;
		int sequenceNumber;
		byte oddEvenFrame;
		String deviceID;

		byte frameCode = report[0];
		if (frameCode == Constant.FRAME_CODE_7D) {
			// 7D
			/////
			acknowledge = (report[Constant.TAG_INNDEX] & Constant.ACK_MASK) != 0;
			if (acknowledge) {
				sequenceNumber = StringUtilities.parseInt(report, 1 + Constant.TAG_LEN + Constant.DEVICE_ID_LEN,
						Constant.SEQUENCE_NUM_LENGTH, byteOrder);
				if ((sequenceNumber & 0x0001) != 0) {
					tag = (byte) 0x4F; // Tag, odd, no identification
				} else {
					tag = (byte) 0x0F; // Tag, even, no identification
				}

				String deviceTag = Constant.DEVICE_TYPES[report[Constant.TAG_INNDEX] >>> Constant.TAG_SHIFT];
				deviceID = StringUtilities.Byte2HexString(report, Constant.DEVICE_ID_INDEX, Constant.DEVICE_ID_LEN);

				if (deviceTag.equalsIgnoreCase(Constant.DEVICE_TYPES[Constant.CDMA])) {
					deviceID = deviceID.substring(2);

				} else {
					deviceID = deviceID.substring(1);
				}
				if (isCloseDeviceListenWindow) {
					// no need to keep device in listen window since there is no UDP command waiting
					////////////////////////////////////////////////////////////////////////////////
					tag |= 0b00010000;
					logger.info("AcknowledgeUDPPacket.GetAcknowledgePacket: Message UUID : "
							+ " preparing NACK to device = " + deviceID + " to stop listening:" + "   TAG = 0b");
				} else {
					// keep device in listening window since there is a UDP command for the device
					//////////////////////////////////////////////////////////////////////////////
					logger.info("AcknowledgeUDPPacket.GetAcknowledgePacket: Message UUID : "
							+ " preparing ACK to device = " + deviceID + " to keep listening:");
				}

				packet = new byte[] { START_OF_FRAME_7E_AND_TLV, RESERVED, PAYLOAD_LENGTH, PROTOCOL_ID, tag };
			}
		}
		return packet;

	}

	public String sendATCommand(String deviceID, String message, Timestamp commandSentTimestamp,
			InetAddress listenerInetAddress, int listenerPort) {
		java.sql.Timestamp timestamp = new java.sql.Timestamp(new java.util.Date().getTime());
		message = "AT+XIP";
		// packet sent to device comprises:
		//////////////////////////////////
		// 1) Command text (which is the message parameter to this method
		// 2) Identifier - single ASCII of %
		// 3) Device ID - ASCII bytes
		// 4) Delimiter - single ASCII of ^
		// 5) Part of the command up to 40 ASCII characters (bytes)
		// 6) Delimiter - single ASCII of ^
		// 7) Command Sent Timestamp - 8 bytes of the long containing the nanoseconds

		byte[] timeBytes = String.format("%0" + Constant.TIMESTAMP_BYTES_LEN + "d", commandSentTimestamp.getTime())
				.getBytes();

		byte[] msg = message.getBytes();

		int sendDataLen = Constant.UDP_RESPONSE_IDENTIFIER_LEN + msg.length + Constant.UDP_RESPONSE_DELIMITER_LEN
				+ deviceID.length() + Constant.UDP_RESPONSE_DELIMITER_LEN + timeBytes.length;

		int commandLen = msg.length;
		int dif = sendDataLen - Constant.MAX_IDENTIFIER_TOTAL_LENGTH;
		if (dif > 0) {
			commandLen -= dif;
		}

		sendDataLen += commandLen;

		byte[] partialCommandBytes = new byte[commandLen];
		System.arraycopy(msg, 0, partialCommandBytes, 0, commandLen);

		String partialCommandSTR = new String(partialCommandBytes);

		byte[] sendData = new byte[sendDataLen];

		int destPosition = 0;

		System.arraycopy(msg, 0, sendData, destPosition, msg.length);
		destPosition += msg.length;

		sendData[destPosition] = Constant.UDP_RESPONSE_IDENTIFIER_BYTE;
		destPosition += 1;

		byte[] id = deviceID.getBytes();
		System.arraycopy(id, 0, sendData, destPosition, deviceID.length());
		destPosition += deviceID.length();

		sendData[destPosition] = Constant.UDP_RESPONSE_DELIMITER_BYTE;
		destPosition += 1;

		System.arraycopy(partialCommandBytes, 0, sendData, destPosition, commandLen);
		destPosition += commandLen;

		sendData[destPosition] = Constant.UDP_RESPONSE_DELIMITER_BYTE;
		destPosition += 1;

		System.arraycopy(timeBytes, 0, sendData, destPosition, Constant.TIMESTAMP_BYTES_LEN);
		destPosition += timeBytes.length;

		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, listenerInetAddress, listenerPort);
		InetAddress deviceInetAddressForSocket = listenerInetAddress;
		try {
			socket = SocketObject.getSocket(listenerIp ,listenerPort);
		} catch (SocketException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		

		try {
			socket.send(sendPacket);
			logger.info("AT-command Packet sent on the device from AT-Command Processor");
			logger.info("SendNewUDPToDeviceDirectly: Sent " + message + " to deviceID " + deviceID + " at ip = "
					+ listenerInetAddress + ":" + listenerPort + " on timestamp = " + timestamp + " UDP packet = "
					+ StringUtilities.Byte2HexString(sendData));

			logger.info("SendNewUDPToDeviceDirectly: Sent " + message + " to deviceID " + deviceID + " at ip = "
					+ listenerInetAddress + ":" + listenerPort + " on timestamp = " + timestamp + " UDP packet = "
					+ StringUtilities.Byte2HexString(sendData));
		} catch (IOException ie) {
			ie.printStackTrace();
			logger.info("SendNewUDPToDeviceDirectly: FAILED TO SEND " + message + " to deviceID " + deviceID
					+ " at ip = " + listenerInetAddress + ":" + listenerPort + " on timestamp " + timestamp
					+ " due to exception in socket.send = " + ie.getMessage());
			return Constant.SENDING_FAILED + " " + ie.getMessage();
		}
		return partialCommandSTR;
	}

	@Override
	public DeviceCommand getDeviceCommandFromRedis(String deviceId) {
		List<String> valuesForDevice = redisDeviceCommandRepository.findValuesForDevice(DEVICE_ID_PREFIX + deviceId,
				FIELDS);
		if (valuesForDevice != null && !valuesForDevice.isEmpty()
				&& !(valuesForDevice.get(0) == null && valuesForDevice.get(1) == null && valuesForDevice.get(2) == null
						&& valuesForDevice.get(3) == null)) {
			DeviceCommand device = new DeviceCommand();
		}

		return null;
	}

	@Override
	public void  processATCommand(String deviceId, InetAddress deviceInetAddress,  int devicePort,
			InetAddress listenerInetAddress, int listenerPort)
	{
		java.sql.Timestamp timestamp = new java.sql.Timestamp(new java.util.Date().getTime());
		//get AT command from redis
		//DeviceCommand deviceCommand  = getDeviceCommandFromRedis(deviceId);
		DeviceCommand deviceCommand = new DeviceCommand();
		sendATCommand(deviceId,deviceCommand.getAtCommand(),timestamp,listenerInetAddress,listenerPort);
		
	}
	@Override
	public void processAck(byte[] packet, InetAddress deviceInetAddress, String deviceId, int devicePort,
			InetAddress listenerInetAddress, int listenerPort) {
		
		
		logger.info("Device Command Reuest recieved on AT-command Processor");
		try {

			socket = SocketObject.getSocket(listenerIp,listenerPort);

		} catch (SocketException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (packet != null) {
			byte[] ACKPacket = null;

			boolean isClosedDeviceListenWindow = true;
			String localIP = null;
			try {
				localIP = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e1) {

			}
			// int localPort = socket.getLocalPort();
			int localPort = 0;
			String str = null;

			String command_text = " ";

			if (command_text != null) {
				String status = " ";

				isClosedDeviceListenWindow = false;
			}
			if ((localPort != Constant.FLEET_COMPLETE_VPN_PORT) || (!localIP.equalsIgnoreCase("172.25.15.195"))) {
				ACKPacket = getAcknowledgePacket(isClosedDeviceListenWindow, packet, ByteOrder.BIG_ENDIAN);
				if (ACKPacket != null) {
					DatagramPacket datagramPacket = new DatagramPacket(ACKPacket, ACKPacket.length, deviceInetAddress,
							devicePort);

					str = StringUtilities.Byte2HexString(ACKPacket);
					int timeDifference = 0;

					try {
						socket.send(datagramPacket);
						logger.info("Ack Packet sent on the device from AT-Command Processor to IP " + deviceInetAddress
								+ " and port :" + devicePort);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
