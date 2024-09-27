package com.upao.pe.coderlink.dtos.payment;

import com.upao.pe.coderlink.dtos.offer.JobOfferDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PaymentDTO {

    private LocalDateTime transactionDate;
    private double total;
    private LocalDateTime totalDate;
    private String paymentMethod;
    private String facturation;
    private String status;
    private JobOfferDTO jobOffer;
}
