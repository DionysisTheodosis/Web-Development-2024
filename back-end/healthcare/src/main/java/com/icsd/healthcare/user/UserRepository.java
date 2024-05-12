package com.icsd.healthcare.user;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByFirstName(String firstName);
    Optional<User> findByEmail(String email);


}
