package com.upao.pe.coderlink.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
@Entity
public class Customer extends User{

    @Column(name = "company_name", length = 20, nullable = false)
    private String companyName;

    @Column(name = "ruc", nullable = false)
    private int ruc;

    @Column(name = "phone", nullable = false)
    private int phone;

    // Mapear uno a muchos con JobOffer y Project
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Project> projects;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<JobOffer> offers;
}
