package com.upao.pe.coderlink.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationUserRequest {
    @NotBlank(message = "A data with only blank spaces is not valid")
    private String username;
    @NotBlank(message = "A data with only blank spaces is not valid")
    private String password;
}
