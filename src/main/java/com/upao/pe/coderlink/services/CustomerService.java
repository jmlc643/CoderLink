package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.customer.CreateCustomerRequest;
import com.upao.pe.coderlink.dtos.customer.CustomerDTO;
import com.upao.pe.coderlink.dtos.joboffer.JobOfferDTO;
import com.upao.pe.coderlink.exceptions.ResourceExistsException;
import com.upao.pe.coderlink.exceptions.ResourceNotExistsException;
import com.upao.pe.coderlink.models.Customer;
import com.upao.pe.coderlink.models.JobOffer;
import com.upao.pe.coderlink.models.enums.TypeUser;
import com.upao.pe.coderlink.repos.CustomerRepository;
import com.upao.pe.coderlink.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    // CREATE
    public Customer createCustomer(CreateCustomerRequest request){
        Customer customer = new Customer(request.getCompanyName(), request.getRuc(), request.getPhone(), new ArrayList<>());
        if(userRepository.existsUserByNames(customer.getNames())){
            throw new ResourceExistsException("User "+customer.getNames()+" exists");
        }
        if(userRepository.existsUserByEmail(customer.getEmail())){
            throw new ResourceExistsException("User with email "+customer.getEmail()+" exists");
        }
        customer.setUsername(request.getUsername());
        customer.setDni(request.getDni());
        customer.setNames(request.getNames());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        customer.setTypeUser(TypeUser.valueOf(request.getTypeUser().toUpperCase()));
        customer.setEnabled(false);
        return customerRepository.save(customer);
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
        return new CustomerDTO(customer.getUsername(), customer.getNames(), customer.getLastName(), customer.getEmail(), customer.getCompanyName(), customer.getRuc(), customer.getPhone(), offers);
    }

    // GET CUSTOMER
    public Customer getCustomer(String companyName){
        Optional<Customer> customer = customerRepository.findByCompanyName(companyName);
        if(customer.isEmpty()){
            throw new ResourceNotExistsException("Company "+companyName+" has not been found");
        }
        return customer.get();
    }
}
