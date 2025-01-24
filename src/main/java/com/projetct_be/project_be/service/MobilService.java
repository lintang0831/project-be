package com.projetct_be.project_be.service;

import com.projetct_be.project_be.DTO.MobilDTO;
import com.projetct_be.project_be.model.Mobil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MobilService {
    List<Mobil> getAllMobil();

    List<Mobil> getAllByAdmin(Long idAdmin);

    Optional<Mobil> getMobilById(Long id);

    MobilDTO tambahMobilDTO(Long idAdmin, MobilDTO mobilDTO);

    MobilDTO editMobilDTO(Long id, Long idAdmin, String mobilJson, MultipartFile file) throws IOException;

    void deleteMobil(Long id) throws IOException;
}
