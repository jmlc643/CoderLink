package com.upao.pe.coderlink.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {
    private String names;
    private String lastNames;
    private String email;
    private String companyName;
    private int ruc;
    private int phone;
}
