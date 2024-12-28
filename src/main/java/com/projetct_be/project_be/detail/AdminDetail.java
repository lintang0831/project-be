package com.projetct_be.project_be.detail;

  // Ganti model User menjadi Admin
import com.projetct_be.project_be.model.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class AdminDetail implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;

    // Constructor untuk AdminDetail
    public AdminDetail(Long id, String username, String email, String password, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Static method untuk membangun AdminDetail dari Admin
    public static AdminDetail buildAdmin(Admin admin) {  // Ganti User menjadi Admin
        return new AdminDetail(
                admin.getId(),
                admin.getUsername(),
                admin.getEmail(),
                admin.getPassword(),
                "ADMIN"  // Memberikan role sebagai ADMIN
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Mengembalikan role ADMIN yang diberikan pada admin
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
