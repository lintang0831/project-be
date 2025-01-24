package com.projetct_be.project_be.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetct_be.project_be.DTO.MobilDTO;
import com.projetct_be.project_be.exception.NotFoundException;
import com.projetct_be.project_be.model.Admin;
import com.projetct_be.project_be.model.Mobil;
import com.projetct_be.project_be.repository.AdminRepository;
import com.projetct_be.project_be.repository.MobilRepository;
import com.projetct_be.project_be.service.MobilService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MobilImpl implements MobilService {

    private static final String BASE_URL = "https://s3.lynk2.co/api/s3";
    private final MobilRepository mobilRepository;
    private final AdminRepository adminRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public MobilImpl(MobilRepository mobilRepository, AdminRepository adminRepository) {
        this.mobilRepository = mobilRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Mobil> getAllMobil() {
        return mobilRepository.findAll();
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
                .orElseThrow(() -> new NotFoundException("Admin not found"));

        Mobil mobil = new Mobil();
        mobil.setAdmin(admin);
        mobil.setNamaMobil(mobilDTO.getNamaMobil());
        mobil.setHargaMobil(mobilDTO.getHargaMobil());

        // Set fotoUrl dari DTO
        mobil.setFotoUrl(mobilDTO.getFotoUrl());

        Mobil savedMobil = mobilRepository.save(mobil);

        MobilDTO result = new MobilDTO();
        result.setId(savedMobil.getId());
        result.setNamaMobil(savedMobil.getNamaMobil());
        result.setHargaMobil(savedMobil.getHargaMobil());
        result.setFotoUrl(savedMobil.getFotoUrl());

        return result;
    }

    @Override
    public MobilDTO editMobilDTO(Long id, Long idAdmin, String mobilJson, MultipartFile file) throws IOException {
        Mobil existingMobil = mobilRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mobil tidak ditemukan"));

        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new NotFoundException("Admin tidak ditemukan"));

        ObjectMapper objectMapper = new ObjectMapper();
        MobilDTO mobilDTO = objectMapper.readValue(mobilJson, MobilDTO.class);

        existingMobil.setAdmin(admin);
        existingMobil.setNamaMobil(mobilDTO.getNamaMobil());
        existingMobil.setHargaMobil(mobilDTO.getHargaMobil());

        if (file != null) {
            String fotoUrl = uploadFoto(file);
            existingMobil.setFotoUrl(fotoUrl);
        }

        Mobil updatedMobil = mobilRepository.save(existingMobil);

        MobilDTO result = new MobilDTO();
        result.setId(updatedMobil.getId());
        result.setNamaMobil(updatedMobil.getNamaMobil());
        result.setHargaMobil(updatedMobil.getHargaMobil());
        result.setFotoUrl(updatedMobil.getFotoUrl());

        return result;
    }

    @Override
    public void deleteMobil(Long id) throws IOException {
        mobilRepository.deleteById(id);
    }

    private String uploadFoto(MultipartFile file) throws IOException {
        String uploadUrl = BASE_URL + "/uploadFoto";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return extractFileUrlFromResponse(response.getBody());
        } else {
            throw new IOException("Failed to upload file: " + response.getStatusCode());
        }
    }

    private String extractFileUrlFromResponse(String responseBody) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(responseBody);
        JsonNode dataNode = jsonResponse.path("data");
        return dataNode.path("url_file").asText();
    }
}
