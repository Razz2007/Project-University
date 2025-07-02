package com.sena.crud_2899747.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.crud_2899747.model.Users;


public interface Iuser extends JpaRepository<Users, Integer> {
    
    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    List<Users> findAllByEnabled(boolean enabled);

}
