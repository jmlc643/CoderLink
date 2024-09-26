package com.upao.pe.coderlink.dtos.skill;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSkillRequest {
    @NotEmpty(message = "Empty Data")
    @NotBlank(message = "A data with only blank spaces is not valid")
    private String name;
    @NotEmpty(message = "Empty Data")
    @NotBlank(message = "A data with only blank spaces is not valid")
    private String description;
}
