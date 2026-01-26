package com.smarthome.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room")
@SQLDelete(sql = "UPDATE room SET deleted_date = NOW() WHERE room_id = ? AND version = ?")
@Where(clause = "deleted_date IS NULL")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String roomName;

    @CreationTimestamp
    private LocalDateTime creationTimeStamp;

    @UpdateTimestamp
    private LocalDateTime updationTimeStamp;

    private LocalDateTime deletedDate;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @OneToMany(mappedBy = "room")
    private Set<Device> devicesInRoom = new HashSet<>();
}
