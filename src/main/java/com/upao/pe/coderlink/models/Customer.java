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

    // Mapear uno a muchos con JobOffer y Project
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Project> projects;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<JobOffer> offers;
}
