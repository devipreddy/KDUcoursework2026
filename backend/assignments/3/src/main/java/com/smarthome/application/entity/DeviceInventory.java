package com.smarthome.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Where;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "device_inventory")
@SQLDelete(sql = "UPDATE device_inventory SET deleted_date = NOW() WHERE kickston_id = ? AND version = ?")
@Where(clause = "deleted_date IS NULL")
public class DeviceInventory {

    @Id
    @Column(name = "kickston_id",nullable = false, length = 6)
    private String kickstonId;

    @Column(name = "device_username", nullable = false)
    private String deviceUsername;

    @Column(name = "device_password", nullable = false)
    private String devicePassword;

    @Column(name = "manufacture_date_time")
    private LocalDateTime manufactureDateTime;

    @UpdateTimestamp
    private LocalDateTime updationTimeStamp;

    @Column(name = "manufacture_factory_place", nullable = true)
    private String placeOfManufacture;

    @OneToOne(mappedBy = "inventoryDevice", fetch = FetchType.LAZY)
    private Device device;

    private java.time.LocalDateTime deletedDate;

    @Version
    private Long version;
}
