package com.upao.pe.coderlink.controllers;

import com.upao.pe.coderlink.dtos.customer.CustomerDTO;
import com.upao.pe.coderlink.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/add-favorite/{customUser}/{devUser}")
    public CustomerDTO addFavorite(@PathVariable String customUser, @PathVariable String devUser){
        return customerService.addFavorite(customUser, devUser);
    }

    @DeleteMapping("/delete-favorite/{customUser}/{devUser}")
    public CustomerDTO deleteFavorite(@PathVariable String customUser, @PathVariable String devUser){
        return customerService.deleteFavorite(customUser, devUser);
    }
}
