package com.example.hospitaljpa.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "shift_type")
@Data
@NoArgsConstructor
public class ShiftTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String name;

    @OneToMany(mappedBy = "shiftType")
    @Schema(hidden = true)
    private List<ShiftEntity> shifts;


}
