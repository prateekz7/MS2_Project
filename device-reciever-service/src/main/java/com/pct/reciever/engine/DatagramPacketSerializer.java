package com.pct.reciever.engine;

import java.net.DatagramPacket;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class DatagramPacketSerializer implements Serializer<DatagramPacket> {

	@Override
	public byte[] serialize(String s, DatagramPacket quote) {
		byte[] retVal = null;

		// ObjectMapper objectMapper = new
		// ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);;
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE); // turn off everything
		objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); // only use fields
		try {

			retVal = objectMapper.writeValueAsString(quote).getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

}
