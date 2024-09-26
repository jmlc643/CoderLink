package com.upao.pe.coderlink.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name = "tokens")
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_token")
    private Long idToken;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Column(name = "expiration_date", nullable = false)
    private final LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(10);

    @Column(name = "activation_date", nullable = true)
    private LocalDateTime activationDate;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;

    public Token(Long idToken, String token, User user){
        this.idToken = idToken;
        this.token = token;
        this.user = user;
    }
}
