package com.ceica.UF2405.repository;

import com.ceica.UF2405.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findFirstByOrderByIdDesc();
}
