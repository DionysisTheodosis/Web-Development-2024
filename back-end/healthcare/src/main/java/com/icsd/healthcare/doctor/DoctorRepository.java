package com.icsd.healthcare.doctor;

import com.icsd.healthcare.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
   Optional<Doctor> findByUser(User user);

    //Optional<Doctor> findById(int id);

    @Query("SELECT d FROM Doctor d WHERE d.user.email = :email")
    Optional<Doctor> findByEmail(String email);
}
