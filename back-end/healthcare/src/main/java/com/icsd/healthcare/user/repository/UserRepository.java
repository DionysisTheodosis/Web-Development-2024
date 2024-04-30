package com.icsd.healthcare.user.repository;


import com.icsd.healthcare.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByFirstName(String firstName);

}
