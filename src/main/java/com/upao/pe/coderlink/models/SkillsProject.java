package com.upao.pe.coderlink.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "skills_projects")
@Entity
public class SkillsProject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_skill_project")
    private Long idSkillProject;

    // Hacer relación muchos a uno con Skill y Project
    @ManyToOne
    @JoinColumn(name = "id_skill", nullable = false)
    private Skill skill;

    @ManyToOne
    @JoinColumn(name = "id_project", nullable = false)
    @JsonIgnore
    private Project project;
}
