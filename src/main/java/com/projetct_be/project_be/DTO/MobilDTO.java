package com.projetct_be.project_be.DTO;

public class MobilDTO {
    private Long id;
    private Long idAdmin;
    private String namaMobil;
    private Double hargaMobil;

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaMobil() {
        return namaMobil;
    }

    public void setNamaMobil(String namaMobil) {
        this.namaMobil = namaMobil;
    }

    public Double getHargaMobil() {
        return hargaMobil;
    }

    public void setHargaMobil(Double hargaMobil) {
        this.hargaMobil = hargaMobil;
    }
}
