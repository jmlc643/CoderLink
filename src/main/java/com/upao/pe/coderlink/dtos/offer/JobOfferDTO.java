package com.upao.pe.coderlink.dtos.offer;

import com.upao.pe.coderlink.dtos.postulation.PostulationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class JobOfferDTO {
    private String message;
    private double budget;
    private String duration;
    private LocalDateTime publicationDate;
    private PostulationDTO postulation;
}