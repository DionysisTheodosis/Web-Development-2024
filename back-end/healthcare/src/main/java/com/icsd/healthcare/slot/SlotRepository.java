package com.icsd.healthcare.slot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {

    //public Slot findBySlotDateTime(LocalDateTime dateTime);



}