package com.upao.pe.coderlink.dtos.offer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateJobOfferRequest {
    @NotNull(message = "Null Data")
    private double budget;
    @NotEmpty(message = "Empty Data")
    @NotBlank(message = "A data with only blank spaces is not valid")
    private String customerUsername;
    @NotNull(message = "Null Data")
    private Long postulationId;
}
