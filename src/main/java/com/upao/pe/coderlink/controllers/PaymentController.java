package com.upao.pe.coderlink.controllers;

import com.upao.pe.coderlink.dtos.payment.*;
import com.upao.pe.coderlink.services.CheckoutService;
import com.upao.pe.coderlink.services.PaymentService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    CheckoutService checkoutService;

    @GetMapping("/list/")
    public List<PaymentDTO> listPayments(){
        return paymentService.listPayments();
    }

    @PostMapping("/create/")
    public PaymentDTO createPayment(@Valid @RequestBody CreatePaymentRequest request){
        return paymentService.createPayment(request);
    }

    @PostMapping("/contract/{id}")
    public PaymentDTO hireDev(@PathVariable Long id){
        return paymentService.hireDev(id);
    }

    @PostMapping("/create-order")
    public ResponseEntity<PaymentOrderResponse> createPaymentOrder(
            @RequestBody PaymentOrderRequest request,
            @RequestParam(required = false, defaultValue = "paypal") String paymentProvider
    ) throws MessagingException {
        PaymentOrderResponse response = checkoutService.createPayment(request.getPaymentId(), request.getReturnUrl(), request.getCancelUrl());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/capture")
    public ResponseEntity<PaymentCaptureResponse> capturePaymentOrder(
            @RequestBody CaptureOrderRequest request,
            @RequestParam(required = false, defaultValue = "paypal") String paymentProvider
    ) throws MessagingException {
        PaymentCaptureResponse response = checkoutService.capturePayment(request.getOrderId());

        if (response.isCompleted()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
