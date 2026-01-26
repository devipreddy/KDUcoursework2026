package com.smarthome.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "user_home_membership")
@SQLDelete(sql = "UPDATE user_home_membership SET deleted_date = NOW() WHERE membership_id = ? AND version = ?")
@Where(clause = "deleted_date IS NULL")
public class UserHomeMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membershipId;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SystemUser user;

}
