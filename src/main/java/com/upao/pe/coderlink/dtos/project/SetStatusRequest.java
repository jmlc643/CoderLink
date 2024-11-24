package com.upao.pe.coderlink.dtos.project;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetStatusRequest {
    private Long id;
    private String status;
}
