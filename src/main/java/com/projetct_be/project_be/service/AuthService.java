package com.projetct_be.project_be.service;

import com.projetct_be.project_be.detail.AdminDetail;  // Ganti UserDetail dengan AdminDetail
import com.projetct_be.project_be.model.Admin;  // Ganti User dengan Admin
import com.projetct_be.project_be.model.LoginRequest;
import com.projetct_be.project_be.repository.AdminRepository;  // Ganti UserRepository dengan AdminRepository
import com.projetct_be.project_be.securityNew.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    @Autowired
    AdminRepository adminRepository;  // Ganti UserRepository dengan AdminRepository

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mengubah loadUserByUsername untuk menggunakan Admin
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> adminOptional = adminRepository.findByEmail(username);  // Ganti User dengan Admin
        if (adminOptional.isPresent()) {
            return AdminDetail.buildAdmin(adminOptional.get());  // Ganti UserDetail dengan AdminDetail
        }

        throw new UsernameNotFoundException("Admin not found with username: " + username);  // Ganti User menjadi Admin
    }

    // Metode untuk autentikasi admin
    public Map<String, Object> authenticate(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        UserDetails userDetails = loadUserByUsername(email);

        // Memeriksa kecocokan password
        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Email atau password yang Anda masukkan salah");
        }

        // Generate token
        String token = jwtTokenUtil.generateToken(userDetails);

        // Menyusun data respons
        Map<String, Object> adminData = new HashMap<>();
        adminData.put("id", ((AdminDetail) userDetails).getId());  // Ganti UserDetail menjadi AdminDetail
        adminData.put("email", userDetails.getUsername());
        adminData.put("role", ((AdminDetail) userDetails).getRole());  // Ganti UserDetail menjadi AdminDetail

        Map<String, Object> response = new HashMap<>();
        response.put("data", adminData);
        response.put("token", token);

        return response;
    }
}
