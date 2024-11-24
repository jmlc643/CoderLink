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

    //Mapear muchos a muchos con Developer
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Developer.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "favorites", joinColumns = @JoinColumn(name = "id_customer"), inverseJoinColumns = @JoinColumn(name = "id_developer"))
    private List<Developer> developers;
}
