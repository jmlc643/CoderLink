package com.upao.pe.coderlink.dtos.project;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AskChangesRequest {
    private String customerName;
    private String devName;
    private Long idProject;
    private String message;
}
