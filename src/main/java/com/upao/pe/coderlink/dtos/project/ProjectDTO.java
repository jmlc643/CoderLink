package com.upao.pe.coderlink.dtos.project;

import com.upao.pe.coderlink.dtos.joboffer.JobOfferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class ProjectDTO {
    private String name;
    private String description;
    private String milestones;
    private String presentation;
    private String revision;
    private String status;
    private String category;
    private String qualification;
    private LocalDate createdAt;
    private List<JobOfferDTO> offers;
}
