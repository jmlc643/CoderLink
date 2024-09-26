package com.upao.pe.coderlink.controllers;

import com.upao.pe.coderlink.dtos.customer.CreateCustomerRequest;
import com.upao.pe.coderlink.dtos.customer.CustomerDTO;
import com.upao.pe.coderlink.dtos.developer.CreateDeveloperRequest;
import com.upao.pe.coderlink.dtos.developer.DeveloperDTO;
import com.upao.pe.coderlink.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register/customer/")
    public CustomerDTO createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return authService.registerCustomer(request);
    }

    @PostMapping("/register/developer/")
    public DeveloperDTO createDeveloper(@Valid @RequestBody CreateDeveloperRequest request){
        return authService.registerDeveloper(request);
    }

    @GetMapping("/confirmation-token/{token}")
    public String activateAccount(@PathVariable String token){
        return authService.activateAccount(token);
    }
}
