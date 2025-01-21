package com.projetct_be.project_be.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetct_be.project_be.DTO.MobilDTO;
import com.projetct_be.project_be.model.Mobil;
import com.projetct_be.project_be.service.MobilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam("mobil") String mobilJson,
            @RequestParam("file") MultipartFile file) throws IOException {

        // Convert the mobil JSON string to MobilDTO
        ObjectMapper objectMapper = new ObjectMapper();
        MobilDTO mobilDTO = objectMapper.readValue(mobilJson, MobilDTO.class);

        // Upload the photo and get the photo URL from MobilImpl
        String fotoUrl = mobilService.uploadFoto(file);  // Call the uploadFoto from the service implementation

        // Set the photo URL in the DTO
        mobilDTO.setFotoUrl(fotoUrl);

        // Save the mobil with the photo URL
        MobilDTO savedMobil = mobilService.tambahMobilDTO(idAdmin, mobilDTO);

        // Log to ensure the fotoUrl is set correctly
        System.out.println("Saved Mobil: " + savedMobil);

        return ResponseEntity.ok(savedMobil);
    }

    @PutMapping("/mobil/editById/{id}")
    public ResponseEntity<MobilDTO> editMobil(
            @PathVariable Long id,
            @RequestParam Long idAdmin,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam String mobil) throws IOException {

        // Deserialize the mobil JSON to get car details
        ObjectMapper objectMapper = new ObjectMapper();
        MobilDTO mobilDTO = objectMapper.readValue(mobil, MobilDTO.class);

        // If a new file is provided, upload it and update the fotoUrl
        if (file != null) {
            String fotoUrl = mobilService.editUploadFoto(id, file);
            mobilDTO.setFotoUrl(fotoUrl);
        }

        // Edit the other mobil fields without photo
        MobilDTO updatedMobil = mobilService.editMobilDTO(id, idAdmin, mobilDTO);
        return ResponseEntity.ok(updatedMobil);
    }

    @DeleteMapping("/mobil/delete/{id}")
    public ResponseEntity<Void> deleteMobil(@PathVariable Long id) throws IOException {
        mobilService.deleteMobil(id);
        return ResponseEntity.noContent().build();
    }
}
