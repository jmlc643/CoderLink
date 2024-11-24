package com.upao.pe.coderlink.controllers;

import com.upao.pe.coderlink.dtos.customer.CustomerDTO;
import com.upao.pe.coderlink.dtos.customer.EmailRequest;
import com.upao.pe.coderlink.dtos.customer.UpdateCustomerRequest;
import com.upao.pe.coderlink.dtos.customer.VerificationRequest;
import com.upao.pe.coderlink.dtos.user.AuthResponse;
import com.upao.pe.coderlink.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list/")
    public List<CustomerDTO> listCustomers(){return customerService.listCustomer();}

    @GetMapping("/get-customer/{username}")
    public CustomerDTO getCustomer(@PathVariable String username){
        return customerService.returnCustomerDTO(customerService.getCustomer(username));
    }

    @GetMapping("/add-favorite/{customUser}/{devUser}")
    public CustomerDTO addFavorite(@PathVariable String customUser, @PathVariable String devUser){
        return customerService.addFavorite(customUser, devUser);
    }

    @DeleteMapping("/delete-favorite/{customUser}/{devUser}")
    public CustomerDTO deleteFavorite(@PathVariable String customUser, @PathVariable String devUser){
        return customerService.deleteFavorite(customUser, devUser);
    }

    @PutMapping("/edit-customer/{username}")
    public CustomerDTO updateCustomer(@RequestBody UpdateCustomerRequest request, @PathVariable String username){
        return customerService.updateCustomer(request, username);
    }

    @PostMapping("/send-code/")
    public ResponseEntity<Void> sendVerificationCode(@RequestBody EmailRequest request) {
        customerService.sendVerificationCode(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-code/")
    public ResponseEntity<Void> verifyEmailCode(@RequestBody VerificationRequest request) {
        customerService.verifyCode(request);
        return ResponseEntity.ok().build();
    }
}
