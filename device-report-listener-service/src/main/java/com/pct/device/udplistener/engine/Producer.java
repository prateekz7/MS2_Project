package com.pct.device.udplistener.engine;

import java.net.DatagramPacket;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

	public Producer() {
		System.out.println("intilize producer---");
	}

	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	private static final String TOPIC = "users";

	@Autowired
	private KafkaTemplate<DatagramPacket, String> kafkaTemplate;

	String bootstrapeserver = "127.0.0.1:9092";

	public void sendMessage(DatagramPacket rawData, String topic) {
		String bootstrapeserver = "127.0.0.1:9092";

		System.out.println("--in producser----topic--" + topic);

		Properties props = new Properties();
		props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DatagramPacketSerializer.class.getName());

		logger.info(String.format("#### -> Producing message -> %s", rawData.toString()));
		org.apache.kafka.clients.producer.KafkaProducer<String, DatagramPacket> first_producer = new org.apache.kafka.clients.producer.KafkaProducer<String, DatagramPacket>(
				props);

		ProducerRecord<String, DatagramPacket> record = new ProducerRecord<String, DatagramPacket>(topic, rawData);
		first_producer.send(record);
		first_producer.flush();
		first_producer.close();
		System.out.println("Hello World!");
	}
}
