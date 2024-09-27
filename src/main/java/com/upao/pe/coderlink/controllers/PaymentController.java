package com.upao.pe.coderlink.controllers;

import com.upao.pe.coderlink.dtos.payment.CreatePaymentRequest;
import com.upao.pe.coderlink.dtos.payment.PaymentDTO;
import com.upao.pe.coderlink.services.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create/")
    public PaymentDTO createPayment(@Valid @RequestBody CreatePaymentRequest request){
        return paymentService.createPayment(request);
    }

    @PostMapping("/contract/{id}")
    public PaymentDTO hireDev(@PathVariable Long id){
        return paymentService.hireDev(id);
    }
}
