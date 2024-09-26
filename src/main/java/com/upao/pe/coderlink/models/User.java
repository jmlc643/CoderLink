package com.upao.pe.coderlink.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"names", "last_name"})})
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "dni", length = 9)
    private int dni;

    @Column(name = "names", length = 50)
    private String names;

    @Column(name = "last_name", length = 20)
    private String lastName;

    @Column(name = "email", length = 20)
    private String email;

    @Column(name = "password", length = 20)
    private String password;

    @Column(name = "type_user", length = 20)
    private String typeUser;
}
