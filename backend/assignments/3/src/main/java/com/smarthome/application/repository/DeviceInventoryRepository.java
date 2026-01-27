package com.smarthome.application.repository;

import com.smarthome.application.entity.DeviceInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceInventoryRepository extends JpaRepository<DeviceInventory, String> {
    @Query("SELECT di FROM DeviceInventory di WHERE di.deletedDate IS NULL")
    List<DeviceInventory> findAllActiveDeviceInventories();

    @Query("SELECT di FROM DeviceInventory di WHERE di.deletedDate IS NOT NULL")
    List<DeviceInventory> findAllDeletedDeviceInventories();

    @Query("SELECT di FROM DeviceInventory di WHERE di.kickstonId = :id AND di.deletedDate IS NULL")
    DeviceInventory findActiveDeviceInventoryById(@Param("id") String id);
}
