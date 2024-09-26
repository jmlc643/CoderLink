package com.upao.pe.coderlink.dtos.joboffer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class JobOfferDTO {
    private String description;
    private double budget;
    private LocalDate duration;
    private LocalDate publicationDate;
}
