package com.pct.device.command.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Abhishek on 19/03/21
 */

@Configuration
public class RedisConfig {

	@Value("${redis.host}")
	private String redisHost;
	@Value("${redis.port}")
	private int redisPort;

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConFactory = null;
		try {
			RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost,
					redisPort);
			jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
			jedisConFactory.getPoolConfig().setMaxTotal(50);
			jedisConFactory.getPoolConfig().setMaxIdle(50);

		} catch (RedisConnectionFailureException e) {
			e.getMessage();
		}
		return jedisConFactory;
	}

	/*
	 * @Bean JedisConnectionFactory jedisConnectionFactory() {
	 * JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
	 * jedisConFactory.setHostName("localhost"); jedisConFactory.setPort(6379);
	 * return jedisConFactory; }
	 */

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setDefaultSerializer(stringRedisSerializer());
		return template;
	}

	@Bean
	public StringRedisSerializer stringRedisSerializer() {
		return new StringRedisSerializer();
	}

}
