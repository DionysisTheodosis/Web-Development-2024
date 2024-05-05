package com.icsd.healthcare.user.repository;


import com.icsd.healthcare.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByFirstName(String firstName);
    Optional<User> findByEmail(String email);


}
