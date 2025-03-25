package com.icsd.healthcare.user;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User,Integer> {


    Optional<User> findByEmail(String email);
    boolean existsByPersonalIDOrEmail(String personalID, String email);
    boolean existsByEmail(String email);
    Optional<User> findByPassword(String password);
    Optional<Set<User>> findAllByUserRole(UserRole role);

}
