package com.projetct_be.project_be.controller;

;
import com.projetct_be.project_be.DTO.MobilDTO;
import com.projetct_be.project_be.model.Mobil;
import com.projetct_be.project_be.service.MobilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class MobilController {

    private final MobilService mobilService;

    public MobilController(MobilService mobilService) {
        this.mobilService = mobilService;
    }

    @GetMapping("/mobil/all")
    public ResponseEntity<List<Mobil>> getAllMobil() {
        List<Mobil> mobilList = mobilService.getAllMobil();
        return ResponseEntity.ok(mobilList);
    }

    @GetMapping("/mobil/getAllByAdmin/{idAdmin}")
    public ResponseEntity<List<Mobil>> getAllByAdmin(@PathVariable Long idAdmin) {
        List<Mobil> mobilList = mobilService.getAllByAdmin(idAdmin);
        return ResponseEntity.ok(mobilList);
    }

    @GetMapping("/mobil/getById/{id}")
    public ResponseEntity<Mobil> getMobilById(@PathVariable Long id) {
        Optional<Mobil> mobil = mobilService.getMobilById(id);
        return mobil.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/mobil/tambah/{idAdmin}")
    public ResponseEntity<MobilDTO> tambahMobil(
            @PathVariable Long idAdmin,
            @RequestBody MobilDTO mobilDTO) {
        MobilDTO savedMobil = mobilService.tambahMobilDTO(idAdmin, mobilDTO);
        return ResponseEntity.ok(savedMobil);
    }

    @PutMapping(value = "/mobil/editById/{id}")
    public ResponseEntity<MobilDTO> editMobil(
            @PathVariable Long id,
            @RequestParam Long idAdmin,
            @RequestBody MobilDTO mobilDTO) throws IOException {
        // Edit mobil tanpa foto
        MobilDTO updatedMobil = mobilService.editMobilDTO(id, idAdmin, mobilDTO);
        return ResponseEntity.ok(updatedMobil);
    }

    @DeleteMapping("/mobil/delete/{id}")
    public ResponseEntity<Void> deleteMobil(@PathVariable Long id) throws IOException {
        mobilService.deleteMobil(id);
        return ResponseEntity.noContent().build();
    }
}
