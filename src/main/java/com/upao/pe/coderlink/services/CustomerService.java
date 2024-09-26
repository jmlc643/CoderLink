package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.customer.CreateCustomerRequest;
import com.upao.pe.coderlink.dtos.customer.CustomerDTO;
import com.upao.pe.coderlink.dtos.joboffer.JobOfferDTO;
import com.upao.pe.coderlink.exceptions.ResourceNotExistsException;
import com.upao.pe.coderlink.models.Customer;
import com.upao.pe.coderlink.models.JobOffer;
import com.upao.pe.coderlink.repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    // CREATE
    public CustomerDTO createCustomer(CreateCustomerRequest request){
        Customer customer = new Customer(request.getCompanyName(), request.getRuc(), request.getPhone(), new ArrayList<>());
        customer.setDni(request.getDni());
        customer.setNames(request.getNames());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        customer.setTypeUser(request.getTypeUser());
        return returnCustomerDTO(customerRepository.save(customer));
    }

    // READ
    public List<CustomerDTO> listCustomer(){return customerRepository.findAll().stream().map(this::returnCustomerDTO).toList();}

    // UPDATE

    // DELETE

    // DTO
    public CustomerDTO returnCustomerDTO(Customer customer){
        List<JobOfferDTO> offers = new ArrayList<>();
        for(JobOffer jobOffer: customer.getJobOffers()){
            JobOfferDTO jobOfferDTO = new JobOfferDTO(jobOffer.getDescription(), jobOffer.getBudget(), jobOffer.getDuration(), jobOffer.getPublicationDate());
            offers.add(jobOfferDTO);
        }
        return new CustomerDTO(customer.getNames(), customer.getLastName(), customer.getEmail(), customer.getCompanyName(), customer.getRuc(), customer.getPhone(), offers);
    }

    // GET CUSTOMER
    public Customer getCustomer(String companyName){
        Optional<Customer> customer = customerRepository.findByCompanyName(companyName);
        if(customer.isEmpty()){
            throw new ResourceNotExistsException("Company "+companyName+" has not found");
        }
        return customer.get();
    }
}
