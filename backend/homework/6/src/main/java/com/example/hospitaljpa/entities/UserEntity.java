package com.example.hospitaljpa.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String role;

    @OneToMany(mappedBy="user")
    @JsonIgnore
    @io.swagger.v3.oas.annotations.media.Schema(hidden = true)
    private List<ShiftUserEntity> assignments;

}

