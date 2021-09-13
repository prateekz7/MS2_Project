package com.pct.reciever.engine;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pct.reciever.models.DatagramPkt;
import com.pct.reciever.services.DatapacketNavigation;
import com.pct.reciever.utils.StringUtilities;

@Service
public class Consumer {

	// private final Logger logger = LoggerFactory.getLogger(Producer.class);
	@Autowired
	DatapacketNavigation packetNavigation;

	@Bean
	public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ConsumerFactory<Object, Object> kafkaConsumerFactory) {
		System.out.println("-----in kafkaListenerContainerFactory: ");
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		configurer.configure(factory, kafkaConsumerFactory);
		factory.setRetryTemplate(retryTemplate());
		factory.setRecoveryCallback((context -> {
			if (context.getLastThrowable().getCause() instanceof RecoverableDataAccessException) {
				// here you can do your recovery mechanism where you can put back on to the
				// topic using a Kafka producer
				System.out.println("-----in kafkaListenerContainerFactory: 1");
			} else {
				// here you can log things and throw some custom exception that Error handler
				// will take care of ..
				System.out.println(
						"-----in kafkaListenerContainerFactory: 2--" + context.getLastThrowable().getMessage());
				throw new RuntimeException(context.getLastThrowable().getMessage());

			}
			return new RuntimeException(context.getLastThrowable().getMessage());
		}));
		factory.setErrorHandler(((exceptiousersn, data) -> {
			/*
			 * here you can do you custom handling, I am just logging it same as default
			 * Error handler does If you just want to log. you need not configure the error
			 * handler here. The default handler does it for you. Generally, you will
			 * persist the failed records to DB for tracking the failed records.
			 */
			// log.error("Error in process with Exception {} and the record is {}",
			// exception, data);
			System.out.println("-----in kafkaListenerContainerFactory: 3");
		}));
		return factory;
	}

	private RetryTemplate retryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();
		/*
		 * here retry policy is used to set the number of attempts to retry and what
		 * exceptions you wanted to try and what you don't want to retry.
		 */
		retryTemplate.setRetryPolicy(getSimpleRetryPolicy());
		System.out.println("-----in kafkaListenerContainerFactory: 5");
		return retryTemplate;
	}

	private SimpleRetryPolicy getSimpleRetryPolicy() {
		Map<Class<? extends Throwable>, Boolean> exceptionMap = new HashMap<>();
		exceptionMap.put(IllegalArgumentException.class, false);
		exceptionMap.put(TimeoutException.class, true);

		System.out.println("-----in kafkaListenerContainerFactory: 4" + exceptionMap);
		return new SimpleRetryPolicy(4, exceptionMap, true);
	}

	@KafkaListener(topics = "parsing", containerFactory = "kafkaListenerContainerFactory")
	public void listen(String in) throws TimeoutException, IOException {

		String uuid = UUID.randomUUID().toString();
		// System.out.println("inside consumer");
		System.out.println("-----Received Content of  datagram at kafka consumer " + in);

		DatagramPkt object = JSON.parseObject(in, DatagramPkt.class);

		DatagramPacket datagramPacket = new DatagramPacket(object.getBuf(), object.getBufLength(), object.getAddress(),
				object.getPort());

		packetNavigation.reportTypeCheck(datagramPacket, uuid);
	}

}
