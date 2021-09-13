package com.pct.device.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pct.device.command.entity.DeviceCommand;

@Repository
public interface IDeviceCommandRespository
		extends JpaRepository<DeviceCommand, Long>, JpaSpecificationExecutor<DeviceCommand> {

	@Query("FROM DeviceCommand deviceCommandManager WHERE deviceCommandManager.uuid = :uuid")
	DeviceCommand findByUuid(@Param("uuid") String uuid);
}
