package com.example.authservice.repository;
import com.example.authservice.enitity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    UserDetails findByUsername(String username);
    Auth getByUsername(String username);
    Auth findByUrl(String url);
}
