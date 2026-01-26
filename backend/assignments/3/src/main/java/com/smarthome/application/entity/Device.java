package com.smarthome.application.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "device")
@SQLDelete(sql = "UPDATE device SET deleted_date = NOW() WHERE device_id = ? AND version = ?")
@Where(clause = "deleted_date IS NULL")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;

    @OneToOne
    @JoinColumn(name = "kickston_id", unique = true, nullable = false)
    private DeviceInventory inventoryDevice;

    @CreationTimestamp
    private LocalDateTime creationTimeStamp;

    @UpdateTimestamp
    private LocalDateTime updationTimeStamp;

    private LocalDateTime deletedDate;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
