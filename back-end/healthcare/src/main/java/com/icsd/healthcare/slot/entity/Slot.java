package com.icsd.healthcare.slot.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_slot")
@Entity
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slotID")
    private Integer id;


    @Column(name = "slotDateTime",nullable = false)
    private LocalDateTime slotDateTime;

    @Builder.Default
    @Column(name = "duration", columnDefinition = "int default 1800")
    private Integer duration=1800;

}
