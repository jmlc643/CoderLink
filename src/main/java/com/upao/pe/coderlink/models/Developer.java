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

    @Column(name = "hours_worked", nullable = false)
    private int hoursWorked;

    @Column(name = "payment_rate", length = 30, nullable = false)
    private String paymentRate;

    @Column(name = "work_experience", length = 40, nullable = false)
    private String workExperience;

    @Column(name = "years_experience", length = 10, nullable = false)
    private int yearsExperience;

    // Mapear el uno a muchos con Postulation
    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL)
    private List<Postulation> postulations;
}
