package com.upao.pe.coderlink.dtos.customer;

import com.upao.pe.coderlink.dtos.joboffer.JobOfferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerDTO {
    private String names;
    private String lastNames;
    private String email;
    private String companyName;
    private int ruc;
    private int phone;
    private List<JobOfferDTO> jobOffers;
}
