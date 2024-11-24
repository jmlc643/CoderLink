package com.upao.pe.coderlink.dtos.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.upao.pe.coderlink.dtos.postulation.PostulationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String presentation;
    private String revision;
    private String status;
    private String category;
    private String qualification;
    private double budget;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updateAt;
    private List<PostulationDTO> postulations;
}
