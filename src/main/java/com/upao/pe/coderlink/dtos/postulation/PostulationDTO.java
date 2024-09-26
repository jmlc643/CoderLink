package com.upao.pe.coderlink.dtos.postulation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PostulationDTO {
    private LocalDate postulationDate;
    private String status;
}
