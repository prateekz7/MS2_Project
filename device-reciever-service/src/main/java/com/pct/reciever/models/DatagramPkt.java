package com.pct.reciever.models;

import java.net.InetAddress;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatagramPkt {

	public DatagramPkt() {
		super();
	}

	public DatagramPkt(byte[] buf, int offset, int length, int bufLength, InetAddress address, int port) {
		super();
		this.buf = buf;
		this.offset = offset;
		this.length = length;
		this.bufLength = bufLength;
		this.address = address;
		this.port = port;
	}

	byte[] buf;
	int offset;
	int length;
	int bufLength;
	InetAddress address;
	int port;

}
