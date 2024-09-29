package com.upao.pe.coderlink.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Column(name = "budget", nullable = false)
    private double budget;

    @Column(name = "publication_date", nullable = false)
    private LocalDateTime publicationDate;

    // Mapear el muchos a uno con Customer
    @ManyToOne
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer customer;

    // Mapear el uno a uno con  Postulation
    @OneToOne
    @JoinColumn(name = "id_postulation", nullable = false)
    private Postulation postulation;
}