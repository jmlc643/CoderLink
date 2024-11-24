package com.upao.pe.coderlink.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCustomerRequest {
    private String username;
    private String password;
    private String email;
}
