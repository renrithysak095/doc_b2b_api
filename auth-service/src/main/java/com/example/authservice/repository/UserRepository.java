package com.example.authservice.repository;

import com.example.authservice.enitity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Auth,Long> { }
