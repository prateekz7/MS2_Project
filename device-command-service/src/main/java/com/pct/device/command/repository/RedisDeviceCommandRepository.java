package com.pct.device.command.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author Abhishek on 19/03/21
 */

@Repository
public class RedisDeviceCommandRepository {

	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations hashOperations;

	@Autowired
	public RedisDeviceCommandRepository(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	public void add(String deviceId, Map<String, String> values) {
		hashOperations.putAll(deviceId, values);
	}

	public List<String> findValuesForDevice(String deviceId, List<String> fields) {
		return hashOperations.multiGet(deviceId, fields);
	}
}
