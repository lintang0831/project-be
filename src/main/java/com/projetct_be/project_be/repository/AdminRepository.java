package com.projetct_be.project_be.repository;

import com.projetct_be.project_be.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Menggunakan JPQL untuk mencari Admin berdasarkan username
    Optional<Admin> findByUsername(String username);

    // Menggunakan JPQL untuk mencari Admin berdasarkan email
    Optional<Admin> findByEmail(String email);

    // Menggunakan JPQL untuk mencari semua Admin dengan role 'ADMIN'
    List<Admin> findAllByRole(String role);

    // Menggunakan JPQL untuk mencari Admin berdasarkan role
    List<Admin> findByRole(String role);
}
