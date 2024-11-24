package com.upao.pe.coderlink.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String username;
    private String message;
    private String token;
    private boolean status;
    private String role;
}
