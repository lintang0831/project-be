package com.projetct_be.project_be.detail;


import com.projetct_be.project_be.model.Admin;
import com.projetct_be.project_be.repository.AdminRepository;
import com.projetct_be.project_be.securityNew.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomUserDetails implements UserDetailsService {  // Ganti CustomUserDetails menjadi CustomAdminDetails

    @Autowired
    AdminRepository adminRepository;  // Ganti UserRepository dengan AdminRepository

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> adminOptional = adminRepository.findByUsername(username);  // Ganti User menjadi Admin
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            return AdminDetail.buildAdmin(admin);  // Ganti UserDetail.buidUser menjadi AdminDetail.buildAdmin
        }

        throw new UsernameNotFoundException("Admin Not Found with username: " + username);  // Ganti User menjadi Admin
    }
}
