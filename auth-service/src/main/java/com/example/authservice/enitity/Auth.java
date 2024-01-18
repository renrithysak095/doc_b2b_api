package com.example.authservice.enitity;

import com.example.authservice.response.AuthResponse;
import com.example.authservice.response.UserResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "public")
public class Auth implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 25, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String url;
    private Long deptId;
    private String role;
    private String image;
    private Boolean status;
    @Column(nullable = false)
    private LocalDateTime cred_dt;
    @Column(nullable = false)
    private LocalDateTime last_md;

    public AuthResponse toDto(){
        return new AuthResponse(id,username,url,deptId,role,image,cred_dt);
    }
    public UserResponse toResponse(String token){
        return new UserResponse(id,username,token,deptId,role,image,cred_dt,last_md);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
