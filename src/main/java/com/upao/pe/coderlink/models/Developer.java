package com.upao.pe.coderlink.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "developers")
@Entity
public class Developer extends User{

    @Column(name = "portafolio", length = 30, nullable = false)
    private String portafolio;

    @Column(name = "payment_rate", length = 30, nullable = false)
    private String paymentRate;

    @Column(name = "work_experience", length = 40, nullable = false)
    private String workExperience;

    // Mapear el uno a muchos con Postulation
    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL)
    private List<Postulation> postulations;

    //Mapear muchos a muchos con Skills
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Skill.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "dev_skills", joinColumns = @JoinColumn(name = "id_developer"), inverseJoinColumns = @JoinColumn(name = "id_skill"))
    private List<Skill> skills;
}
