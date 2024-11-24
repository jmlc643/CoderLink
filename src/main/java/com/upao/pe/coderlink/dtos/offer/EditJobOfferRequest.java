package com.upao.pe.coderlink.dtos.offer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditJobOfferRequest {
    private Long id;
    private double budget;
}
