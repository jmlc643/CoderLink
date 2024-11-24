package com.upao.pe.coderlink.dtos.developer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateDeveloperRequest {
    private String username;
    private String portfolio;
    private double paymentRate;
    private String email;
    private String password;
}
