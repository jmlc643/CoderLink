package com.upao.pe.coderlink.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecoveryPasswordRequest {
    @Email(message = "Incorrect format")
    @NotEmpty(message = "Empty Data")
    private String email;
}
