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

    @Column(name = "milestones", nullable = false)
    private String milestones;

    @Column(name = "presentation", nullable = false)
    private String presentation;

    @Column(name = "revision", nullable = false)
    private String revision;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "qualification", nullable = false)
    private String qualification;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    // Mapear el uno a muchos con JobOffer
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<JobOffer> jobOffers;

    // Mapear el muchos a muchos con Skill
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Skill.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "project_skills", joinColumns = @JoinColumn(name = "id_project"), inverseJoinColumns = @JoinColumn(name = "id_skill"))
    private List<Skill> skills;
}
