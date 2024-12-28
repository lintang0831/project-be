package com.projetct_be.project_be.service;



import com.projetct_be.project_be.DTO.MobilDTO;
import com.projetct_be.project_be.model.Mobil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MobilService {
    List<Mobil> getAllMobil();

    default List<Mobil> getAllByAdmin() {
        return getAllByAdmin(null);
    }

    List<Mobil> getAllByAdmin(Long idAdmin);

    Optional<Mobil> getMobilById(Long id);

    MobilDTO tambahMobilDTO(Long idAdmin, MobilDTO mobilDTO);

    MobilDTO editMobilDTO(Long id, Long  idAdmin, MobilDTO mobilDTO) throws IOException;

    void deleteMobil(Long id) throws IOException;

}
