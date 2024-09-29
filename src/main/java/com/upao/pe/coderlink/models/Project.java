package com.upao.pe.coderlink.models;

import com.upao.pe.coderlink.models.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projects")
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_project")
    private Long idProject;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "presentation", nullable = false)
    private String presentation;

    @Column(name = "revision", nullable = false)
    private String revision;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "qualification", nullable = false)
    private String qualification;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Mapear el muchos a uno con Customer
    @ManyToOne
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer customer;

    // Mapear el uno a muchos con Postulation
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Postulation> postulations;
}
