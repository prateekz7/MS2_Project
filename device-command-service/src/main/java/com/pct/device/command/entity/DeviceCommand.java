package com.pct.device.command.entity;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "device_command")
@Getter
@Setter
@NoArgsConstructor
public class DeviceCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate
	@Column(name = "created_date")
	private Instant createdDate;

	@LastModifiedDate
	@Column(name = "updated_date")
	private Instant updatedDate;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "at_command")
	private String atCommand;

	@Column(name = "source")
	private String source;

	@Column(name = "priority")
	private String priority;

	@Column(name = "last_executed")
	private Instant lastExecuted;

	@Column(name = "success")
	private boolean is_success;

}
