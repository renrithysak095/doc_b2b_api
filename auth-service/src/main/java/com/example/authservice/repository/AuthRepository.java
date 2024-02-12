package com.example.authservice.repository;
import com.example.authservice.enitity.Auth;
import com.example.authservice.response.AuthResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    UserDetails findByUsername(String username);
    Auth getByUsername(String username);
    Auth getByUsernameAndProvider(String username, String provider);
    Auth findByUrl(String url);
    List<Auth> findAllByStatus(boolean b);
}
