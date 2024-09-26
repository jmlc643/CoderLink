package com.upao.pe.coderlink.controllers;

import com.upao.pe.coderlink.dtos.customer.CreateCustomerRequest;
import com.upao.pe.coderlink.dtos.customer.CustomerDTO;
import com.upao.pe.coderlink.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/list/")
    public List<CustomerDTO> listCustomers(){return customerService.listCustomer();}

    @PostMapping("/create/")
    public CustomerDTO createCustomerDTO(@Valid @RequestBody CreateCustomerRequest request){
        return customerService.createCustomer(request);
    }
}
