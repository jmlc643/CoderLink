package com.upao.pe.coderlink.models;

import com.upao.pe.coderlink.models.enums.PostulationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "postulations")
@Entity
public class Postulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_postulation")
    private Long idPostulation;

    @Column(name = "publication_date", nullable = false)
    private LocalDateTime publicationDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostulationStatus status;

    // Mapeo Muchos a uno con Developer y Offer
    @ManyToOne
    @JoinColumn(name = "id_developer", nullable = false)
    private Developer developer;

    @ManyToOne
    @JoinColumn(name = "id_project", nullable = false)
    private Project project;
}
