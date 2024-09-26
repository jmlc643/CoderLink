package com.upao.pe.coderlink.dtos.developer;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateDeveloperRequest {
    @NotNull(message = "Null Data")
    @DecimalMax(value = "8", message = "A DNI has 8 characters long")
    private int dni;
    @NotEmpty(message = "Empty Data")
    @NotBlank(message = "A data with only blank spaces is not valid")
    private String names;
    @NotEmpty(message = "Empty Data")
    @NotBlank(message = "A data with only blank spaces is not valid")
    private String lastName;
    @Email(message = "Incorrect format")
    @NotEmpty(message = "Empty Data")
    private String email;
    @NotEmpty(message = "Empty Data")
    @NotBlank(message = "A data with only blank spaces is not valid")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    @NotEmpty(message = "Empty Data")
    @NotBlank(message = "A data with only blank spaces is not valid")
    private String typeUser;
    @NotEmpty(message = "Empty Data")
    @NotBlank(message = "A data with only blank spaces is not valid")
    private String portfolio;
    @NotNull(message = "Null Data")
    private int hoursWorked;
    @NotEmpty(message = "Empty Data")
    @NotBlank(message = "A data with only blank spaces is not valid")
    private String paymentRate;
    @NotEmpty(message = "Empty Data")
    @NotBlank(message = "A data with only blank spaces is not valid")
    private String workExperience;
    @NotNull(message = "Null Data")
    private int yearsExperience;
}
