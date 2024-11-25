package com.upao.pe.coderlink.dtos.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@AllArgsConstructor
public class PaymentOrderRequest {
    private Long paymentId;
    private String returnUrl;
    private String cancelUrl;
}
