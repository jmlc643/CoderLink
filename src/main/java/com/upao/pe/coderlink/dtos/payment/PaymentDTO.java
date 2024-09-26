package com.upao.pe.coderlink.dtos.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PaymentDTO {

    private LocalDate transactionDate;
    private double total;
    private LocalDate totalDate;
    private String paymentMethod;
    private String facturation;
    private LocalDate createdAt;
}
