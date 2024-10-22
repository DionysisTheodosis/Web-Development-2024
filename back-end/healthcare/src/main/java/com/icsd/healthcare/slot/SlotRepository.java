package com.icsd.healthcare.slot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {

    //public Slot findBySlotDateTime(LocalDateTime dateTime);



}
