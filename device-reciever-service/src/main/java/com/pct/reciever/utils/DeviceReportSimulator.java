package com.pct.reciever.utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Instant;

public class DeviceReportSimulator {

	public static void main(String[] args) {
//		Producer producer = new Producer();

		String serverIP = "127.0.0.1";
		int serverPort = 15020;
		System.out.println("time 1  " + Instant.now());
		System.out.println("Reports would be sent to listener address: " + serverIP);

		try {
			// String rawReport =
			// "41542b58524e253031353131353030363436303336335e41542b58524e5e30303031363131313431313937363436";
			// String rawReport="0a223137322e33312e34382e313834222c31353032300d0a";
			String rawReport = "7d010015115006460447000729832bc322e157011bbd29832bc3075f06bd03b432903f00a3001109073d5382000002bde100150140151002031a0163020a18640203056702000400e10131013004534e3a303030303030303030303030303030300048573a302d3030207636350046573a3020322e382e30206e7200e10218ffff4b656e5f4e6574776f726b5f4e616e6f546d6f5f3200e1035d019be64b656e5f4e6574776f726b5f4e616e6f546d6f5f3200026ed64b656e5f4770735f310003f73e4b656e5f4576656e74735f4375746c6173735f37000421e34b656e5f4265686176696f725f4375746c6173735f3130000573dd00e1052e010c0b0000000104010600000000020c05312e323000050c034102080c0c06020003000303060c00070c000a0c00e2100901305700140000103cf0010400b200b2ffff2c00015f8f0003f7720000002a00000071000000000000009a0c050b020000000c000524da0000000c0006a721";
			// System.out.println("rawReport "+rawReport);
			InetAddress inetAddress = InetAddress.getByName(serverIP);

			byte[] buf = StringUtilities.Hex2Byte(rawReport);
			System.out.println("buffer*****  " + buf.toString());

//			DatagramSocket dsocket = new DatagramSocket();
//			dsocket.receive(null);

			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, inetAddress, serverPort);
			// producer.sendMessage(datagramPacket, "parse");
//					DatagramSocket dsocket = new DatagramSocket();
//					dsocket.send(datagramPacket);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
