package com.projetct_be.project_be.repository;


import com.projetct_be.project_be.model.Mobil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobilRepository extends JpaRepository<Mobil, Long> {
    List<Mobil> findByAdminId(Long idAdmin);
}