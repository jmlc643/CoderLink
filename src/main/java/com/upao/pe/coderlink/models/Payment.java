package com.upao.pe.coderlink.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Payment")
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment")
    private Long idPayment;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "totalDate", nullable = false)
    private LocalDate totalDate;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "facturation", nullable = false)
    private String facturation;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    // Mapear el muchos a uno con JobOffer
    @ManyToOne
    @JoinColumn(name = "id_offer", nullable = false)
    private JobOffer jobOffer;
}
