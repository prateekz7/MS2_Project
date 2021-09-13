package com.pct.device.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pct.device.command.entity.DeviceCommandManager;

@Repository
public interface IDeviceCommandRespository
		extends JpaRepository<DeviceCommandManager, Long>, JpaSpecificationExecutor<DeviceCommandManager> {

	@Query("FROM DeviceCommandManager deviceCommandManager WHERE deviceCommandManager.uuid = :uuid")
	DeviceCommandManager findByUuid(@Param("uuid") String uuid);
}
