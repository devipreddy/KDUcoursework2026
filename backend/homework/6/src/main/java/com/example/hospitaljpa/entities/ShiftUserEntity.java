package com.example.hospitaljpa.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="shift_user")
@Data
@NoArgsConstructor
public class ShiftUserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="shift_id")
    @io.swagger.v3.oas.annotations.media.Schema(hidden = true)
    private ShiftEntity shift;

    @ManyToOne
    @JoinColumn(name="user_id")
    @io.swagger.v3.oas.annotations.media.Schema(hidden = true)
    private UserEntity user;

}
