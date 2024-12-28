package com.projetct_be.project_be.impl;

import com.projetct_be.project_be.DTO.PasswordDTO;  // Ganti dengan import yang sesuai
import com.projetct_be.project_be.exception.BadRequestException;
import com.projetct_be.project_be.exception.NotFoundException;

import com.projetct_be.project_be.model.Admin;
import com.projetct_be.project_be.repository.AdminRepository;
import com.projetct_be.project_be.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminImpl implements AdminService {  // Ganti UserService dengan AdminService

    private final AdminRepository adminRepository;  // Ganti UserRepository dengan AdminRepository

    @Autowired
    PasswordEncoder encoder;

    public AdminImpl(AdminRepository adminRepository) {  // Ganti UserRepository dengan AdminRepository
        this.adminRepository = adminRepository;
    }

    // Method untuk registrasi admin
    @Override
    public Admin registerAdmin(Admin admin) {  // Ganti User menjadi Admin
        // Check if the email already exists
        Optional<Admin> existingEmail = adminRepository.findByEmail(admin.getEmail());  // Ganti UserRepository menjadi AdminRepository
        if (existingEmail.isPresent()) {
            throw new BadRequestException("Email sudah digunakan");
        }

        // Check if the username already exists
        Optional<Admin> existingUsername = adminRepository.findByUsername(admin.getUsername());  // Ganti UserRepository menjadi AdminRepository
        if (existingUsername.isPresent()) {
            throw new BadRequestException("Username sudah digunakan");
        }

        // Set role to "ADMIN"
        admin.setRole("ADMIN");  // Set role to ADMIN for admin

        // Encode the password before saving
        admin.setPassword(encoder.encode(admin.getPassword()));

        // Save and return the registered admin
        return adminRepository.save(admin);  // Ganti UserRepository dengan AdminRepository
    }

    // Method untuk mengambil admin berdasarkan ID
    @Override
    public Admin getById(Long id) {  // Ganti User menjadi Admin
        return adminRepository.findById(id)  // Ganti UserRepository menjadi AdminRepository
                .orElseThrow(() -> new NotFoundException("Id Admin Tidak Ditemukan"));
    }

    // Method untuk mengambil semua admin
    @Override
    public List<Admin> getAll() {  // Ganti User menjadi Admin
        return adminRepository.findAll();  // Ganti UserRepository menjadi AdminRepository
    }

    // Method untuk mengedit data admin
    @Override
    public Admin edit(Long id, Admin admin) {  // Ganti User menjadi Admin
        Admin existingAdmin = adminRepository.findById(id)  // Ganti UserRepository menjadi AdminRepository
                .orElseThrow(() -> new NotFoundException("Admin Tidak Ditemukan"));

        existingAdmin.setUsername(admin.getUsername());
        existingAdmin.setEmail(admin.getEmail());
        return adminRepository.save(existingAdmin);  // Ganti UserRepository menjadi AdminRepository
    }

    // Method untuk mengganti password admin
    @Override
    public Admin putPasswordAdmin(PasswordDTO passwordDTO, Long id) {  // Ganti User menjadi Admin
        Admin update = adminRepository.findById(id)  // Ganti UserRepository menjadi AdminRepository
                .orElseThrow(() -> new NotFoundException("Id Admin Tidak Ditemukan"));

        boolean isOldPasswordCorrect = encoder.matches(passwordDTO.getOld_password(), update.getPassword());

        if (!isOldPasswordCorrect) {
            throw new NotFoundException("Password Lama Tidak Sesuai");
        }

        if (passwordDTO.getNew_password().equals(passwordDTO.getConfirm_new_password())) {
            update.setPassword(encoder.encode(passwordDTO.getNew_password()));
            return adminRepository.save(update);  // Ganti UserRepository menjadi AdminRepository
        } else {
            throw new BadRequestException("Password Tidak Sesuai");
        }
    }

    // Method untuk menghapus admin
    @Override
    public Map<String, Boolean> delete(Long id) {  // Ganti User menjadi Admin
        Map<String, Boolean> response = new HashMap<>();
        try {
            adminRepository.deleteById(id);  // Ganti UserRepository menjadi AdminRepository
            response.put("Deleted", Boolean.TRUE);
        } catch (Exception e) {
            response.put("Deleted", Boolean.FALSE);
        }
        return response;
    }
}
