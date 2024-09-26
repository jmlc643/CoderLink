package com.upao.pe.coderlink.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "job_offers")
@Entity
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_offer")
    private Long idOffer;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "budget", nullable = false)
    private double budget;

    @Column(name = "duration", nullable = false)
    private LocalDate duration;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    // Mapear el muchos a uno con Customer y Project
    @ManyToOne
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "id_project", nullable = false)
    private Project project;

    // Mapear el uno a muchos con Payment y Postulation
    @OneToMany(mappedBy = "jobOffer", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @OneToMany(mappedBy = "jobOffer", cascade = CascadeType.ALL)
    private List<Postulation> postulations;
}
