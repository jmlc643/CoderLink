package com.upao.pe.coderlink.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "skill")
@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_skill")
    private Long idSkill;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    //Mapear uno a muchos con skillProjects
    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL)
    private List<SkillsProject> skillsProjects;
}
