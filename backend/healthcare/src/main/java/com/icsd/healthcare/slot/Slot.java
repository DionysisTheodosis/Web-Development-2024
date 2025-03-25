package com.icsd.healthcare.slot;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
    @Column(name = "duration")
    private Integer duration = 1800;

}
