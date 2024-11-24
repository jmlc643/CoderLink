package com.upao.pe.coderlink.dtos.customer;

import com.upao.pe.coderlink.dtos.developer.DeveloperDTO;
import com.upao.pe.coderlink.dtos.offer.JobOfferDTO;
import com.upao.pe.coderlink.dtos.project.ProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerDTO {
    private String username;
    private String names;
    private String lastNames;
    private String email;
    private List<ProjectDTO> projects;
    private List<JobOfferDTO> offers;
    private List<DeveloperDTO> favoritesDevs;
}
