package com.upao.pe.coderlink.dtos.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchProjectRequest {
    @NotEmpty(message = "Empty Data")
    @NotBlank(message = "A data with only blank spaces is not valid")
    private String projectName;
}
