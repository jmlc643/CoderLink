package com.upao.pe.coderlink.dtos.developer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeveloperDTO {
    private String names;
    private String lastNames;
    private String email;
    private String portfolio;
    private int hoursWorked;
    private String paymentRate;
    private String workExperience;
    private int yearsExperience;
}
