package com.projetct_be.project_be.impl;


import com.projetct_be.project_be.DTO.MobilDTO;
import com.projetct_be.project_be.exception.NotFoundException;
import com.projetct_be.project_be.model.Admin;
import com.projetct_be.project_be.model.Mobil;
import com.projetct_be.project_be.repository.AdminRepository;
import com.projetct_be.project_be.repository.MobilRepository;
import com.projetct_be.project_be.service.MobilService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MobilImpl implements MobilService {

    private final MobilRepository mobilRepository;
    private final AdminRepository adminRepository;

    public MobilImpl(MobilRepository mobilRepository, AdminRepository adminRepository) {
        this.mobilRepository = mobilRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Mobil> getAllMobil() {
        return mobilRepository.findAll();
    }

    @Override
    public List<Mobil> getAllByAdmin() {
        return MobilService.super.getAllByAdmin();
    }

    @Override
    public List<Mobil> getAllByAdmin(Long idAdmin) {
        return mobilRepository.findByAdminId(idAdmin);
    }

    @Override
    public Optional<Mobil> getMobilById(Long id) {
        return mobilRepository.findById(id);
    }

    @Override
    public MobilDTO tambahMobilDTO(Long idAdmin, MobilDTO mobilDTO) {
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin dengan ID " + idAdmin + " tidak ditemukan"));

        Mobil mobil = new Mobil();
        mobil.setAdmin(admin);
        mobil.setNamaMobil(mobilDTO.getNamaMobil());
        mobil.setHargaMobil(mobilDTO.getHargaMobil());

        Mobil savedMobil = mobilRepository.save(mobil);

        MobilDTO result = new MobilDTO();
        result.setId(savedMobil.getId());
        result.setIdAdmin(admin.getId());
        result.setNamaMobil(savedMobil.getNamaMobil());
        result.setHargaMobil(savedMobil.getHargaMobil());

        return result;
    }

    @Override
    public MobilDTO editMobilDTO(Long id, Long idAdmin, MobilDTO mobilDTO) throws IOException {
        Mobil existingMobil = mobilRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mobil tidak ditemukan"));

        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin dengan ID " + idAdmin + " tidak ditemukan"));

        existingMobil.setAdmin(admin);
        existingMobil.setNamaMobil(mobilDTO.getNamaMobil());
        existingMobil.setHargaMobil(mobilDTO.getHargaMobil());

        Mobil updatedMobil = mobilRepository.save(existingMobil);

        MobilDTO result = new MobilDTO();
        result.setId(updatedMobil.getId());
        result.setIdAdmin(admin.getId());
        result.setNamaMobil(updatedMobil.getNamaMobil());
        result.setHargaMobil(updatedMobil.getHargaMobil());

        return result;
    }

    @Override
    public void deleteMobil(Long id) throws IOException {
        mobilRepository.deleteById(id);
    }

}
