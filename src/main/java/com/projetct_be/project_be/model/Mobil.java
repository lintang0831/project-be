package com.projetct_be.project_be.model;

import javax.persistence.*;

@Entity
@Table(name = "mobil")
public class Mobil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_mobil")
    private String namaMobil;

    @Column(name = "harga_mobil")
    private Double hargaMobil;

    @ManyToOne
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin admin;

    public Mobil(Long id, Admin admin, String namaMobil, Double hargaMobil) {
        this.id = id;
        this.admin = admin;
        this.namaMobil = namaMobil;
        this.hargaMobil = hargaMobil;
    }

    public Mobil() {
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

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}