package com.upao.pe.coderlink.models;

import com.upao.pe.coderlink.models.enums.TypeUser;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "dni", length = 8)
    private int dni;

    @Column(name = "names", length = 50)
    private String names;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "type_user", length = 20)
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "is_account_no_expired")
    private boolean isAccountNoExpired;

    @Column(name = "is_credential_no_expired")
    private boolean isCredentialNoExpired;

    @Column(name = "is_account_no_locked")
    private boolean isAccountNoLocked;
}
