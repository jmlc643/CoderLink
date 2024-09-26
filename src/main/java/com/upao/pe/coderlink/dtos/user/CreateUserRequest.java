package com.upao.pe.coderlink.dtos.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserRequest {
    @NotNull(message = "Null Data")
    @Size(max = 9, message = "A DNI has 9 characters long")
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
}
