package com.icsd.healthcare.slot.repository;

import com.icsd.healthcare.slot.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {
    public Slot findBySlotDateTime(LocalDateTime dateTime);

}
