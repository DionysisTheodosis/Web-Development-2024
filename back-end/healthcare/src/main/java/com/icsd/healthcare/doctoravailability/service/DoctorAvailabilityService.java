package com.icsd.healthcare.doctoravailability.service;

import com.icsd.healthcare.doctoravailability.dto.AllDoctorAvailabilityResponseDto;
import com.icsd.healthcare.doctoravailability.dto.DoctorAvailabilityDto;
import com.icsd.healthcare.doctoravailability.dto.DoctorAvailabilityResponseDto;
import com.icsd.healthcare.doctoravailability.entity.DoctorAvailability;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public interface DoctorAvailabilityService {


    public DoctorAvailabilityResponseDto saveDoctorAvailability(DoctorAvailabilityDto doctorAvailabilityDto) throws ChangeSetPersister.NotFoundException;

    //public boolean isSlotAvailable(DoctorAvailabilityDto doctorAvailabilityDto);

/*    public boolean deleteDoctorAvailability(DoctorAvailabilityDto doctorAvailabilityDto);*/

}