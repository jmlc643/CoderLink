package com.upao.pe.coderlink.models;

import com.upao.pe.coderlink.models.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime transactionDate;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "totalDate", nullable = false)
    private LocalDateTime totalDate;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "facturation", nullable = false)
    private String facturation;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    // Mapear el uno a uno con JobOffer
    @OneToOne
    @JoinColumn(name = "id_offer", nullable = false)
    private JobOffer jobOffer;
}
