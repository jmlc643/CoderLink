package com.upao.pe.coderlink.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "postulations")
@Entity
public class Postulation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_postulation")
    private Long idPostulation;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Column(name = "status", nullable = false)
    private String status;

    // Mapeo Muchos a uno con Developer y Offer
    @ManyToOne
    @JoinColumn(name = "id_developer", nullable = false)
    private Developer developer;

    @ManyToOne
    @JoinColumn(name = "id_offer", nullable = false)
    private JobOffer jobOffer;
}
