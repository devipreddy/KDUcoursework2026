package com.example.hospitaljpa.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name="shift")
@Data
@NoArgsConstructor
public class ShiftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String shiftName;

    @Column(nullable=false)
    private LocalDate startDate;

    @Column(nullable=false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name="shift_type_id", nullable=false)
    @io.swagger.v3.oas.annotations.media.Schema(hidden = true)
    private ShiftTypeEntity shiftType;

    @OneToMany(mappedBy="shift")
    @Schema(hidden = true)
    private List<ShiftUserEntity> assignments;




}
