package com.smarthome.application.repository;

import com.smarthome.application.entity.Device;
import com.smarthome.application.entity.DeviceInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByInventoryDevice(DeviceInventory inventoryDevice);

    @Query("SELECT d FROM Device d WHERE d.deletedDate IS NULL")
    List<Device> findAllActiveDevices();

    @Query("SELECT d FROM Device d WHERE d.deletedDate IS NOT NULL")
    List<Device> findAllDeletedDevices();

    @Query("SELECT d FROM Device d WHERE d.deviceId = :id AND d.deletedDate IS NULL")
    Device findActiveDeviceById(@Param("id") Long id);

    @Query("SELECT d FROM Device d WHERE d.deletedDate IS NOT NULL AND d.house.houseId = :houseId")
    List<Device> findAllDeletedDevicesByHouseId(@Param("houseId") Long houseId);

}
