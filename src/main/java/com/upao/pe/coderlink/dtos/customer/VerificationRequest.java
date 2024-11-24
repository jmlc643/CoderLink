package com.upao.pe.coderlink.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerificationRequest {
    private String email;
    private String code;
}
