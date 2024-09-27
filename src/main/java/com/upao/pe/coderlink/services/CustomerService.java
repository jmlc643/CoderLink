package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.customer.CreateCustomerRequest;
import com.upao.pe.coderlink.dtos.customer.CustomerDTO;
import com.upao.pe.coderlink.dtos.offer.JobOfferDTO;
import com.upao.pe.coderlink.dtos.postulation.PostulationDTO;
import com.upao.pe.coderlink.dtos.project.ProjectDTO;
import com.upao.pe.coderlink.dtos.skill.SkillDTO;
import com.upao.pe.coderlink.exceptions.ResourceExistsException;
import com.upao.pe.coderlink.exceptions.ResourceNotExistsException;
import com.upao.pe.coderlink.models.*;
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

    @Autowired ProjectService projectService;

    // CREATE
    public Customer createCustomer(CreateCustomerRequest request){
        Customer customer = new Customer(request.getCompanyName(), request.getRuc(), request.getPhone(), new ArrayList<>(), new ArrayList<>());
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
        List<ProjectDTO> projects = new ArrayList<>();
        List<JobOfferDTO> offers = new ArrayList<>();
        for(Project project: customer.getProjects()){
            List<SkillDTO> skills = new ArrayList<>();
            List<PostulationDTO> postulations = new ArrayList<>();
            for(Skill skill : project.getSkills()) {
                SkillDTO skillDTO = new SkillDTO(skill.getName());
                skills.add(skillDTO);
            }
            for(Postulation postulation : project.getPostulations()){
                PostulationDTO postulationDTO = new PostulationDTO(postulation.getDeveloper().getUsername(), postulation.getPublicationDate(), postulation.getStatus().toString());
                postulations.add(postulationDTO);
            }
            ProjectDTO projectDTO = new ProjectDTO(project.getName(), project.getDescription(), project.getMilestones(), project.getPresentation(), project.getRevision(), project.getStatus().toString(), project.getCategory(), project.getQualification(), project.getCreatedAt(), skills, postulations);
            projects.add(projectDTO);
        }
        for(JobOffer offer : customer.getOffers()){
            PostulationDTO postulationDTO = new PostulationDTO(offer.getPostulation().getDeveloper().getUsername(), offer.getPostulation().getPublicationDate(), offer.getPostulation().getStatus().toString());
            JobOfferDTO jobOfferDTO = new JobOfferDTO(offer.getMessage(), offer.getBudget(), offer.getDuration(), offer.getPublicationDate(), postulationDTO);
            offers.add(jobOfferDTO);
        }
        return new CustomerDTO(customer.getUsername(), customer.getNames(), customer.getLastName(), customer.getEmail(), customer.getCompanyName(), customer.getRuc(), customer.getPhone(), projects, offers);
    }

    // GET CUSTOMER
    public Customer getCustomer(String username){
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if(customer.isEmpty()){
            throw new ResourceNotExistsException("Customer "+username+" has not been found");
        }
        return customer.get();
    }
}
