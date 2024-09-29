package com.upao.pe.coderlink.dtos.postulation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostulationDTO {
    private Long id;
    private String devName;
    private LocalDateTime postulationDate;
    private String status;
}
