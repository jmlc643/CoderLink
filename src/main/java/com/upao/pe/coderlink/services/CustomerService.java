package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.customer.*;
import com.upao.pe.coderlink.dtos.developer.DeveloperDTO;
import com.upao.pe.coderlink.dtos.offer.JobOfferDTO;
import com.upao.pe.coderlink.dtos.postulation.PostulationDTO;
import com.upao.pe.coderlink.dtos.project.ProjectDTO;
import com.upao.pe.coderlink.exceptions.ResourceExistsException;
import com.upao.pe.coderlink.exceptions.ResourceNotExistsException;
import com.upao.pe.coderlink.models.*;
import com.upao.pe.coderlink.models.enums.TypeUser;
import com.upao.pe.coderlink.repos.CustomerRepository;
import com.upao.pe.coderlink.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired DeveloperService developerService;

    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>(); // Simula almacenamiento en memoria
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // CREATE
    public Customer createCustomer(CreateCustomerRequest request){
        Customer customer = new Customer(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        if(userRepository.existsUserByNames(customer.getNames())){
            throw new ResourceExistsException("User "+customer.getNames()+" exists");
        }
        if(userRepository.existsUserByEmail(customer.getEmail())){
            throw new ResourceExistsException("User with email "+customer.getEmail()+" exists");
        }
        customer.setUsername(request.getUsername());
        customer.setNames(request.getNames());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        customer.setTypeUser(TypeUser.CUSTOMER);
        customer.setEnabled(false);
        return customerRepository.save(customer);
    }

    // READ
    public List<CustomerDTO> listCustomer(){return customerRepository.findAll().stream().map(this::returnCustomerDTO).toList();}

    // UPDATE
    public CustomerDTO updateCustomer(UpdateCustomerRequest request, String username) {
        Customer customer = getCustomer(username);
        customer.setUsername(request.getUsername());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setEmail(request.getEmail());
        customerRepository.saveAndFlush(customer);
        return returnCustomerDTO(customer);
    }

    // DELETE

    // DTO
    public CustomerDTO returnCustomerDTO(Customer customer){
        List<ProjectDTO> projects = new ArrayList<>();
        List<JobOfferDTO> offers = new ArrayList<>();
        List<DeveloperDTO> developers = new ArrayList<>();
        for(Project project: customer.getProjects()){
            List<PostulationDTO> postulations = new ArrayList<>();
            for(Postulation postulation : project.getPostulations()){
                PostulationDTO postulationDTO = new PostulationDTO(postulation.getIdPostulation(), postulation.getDeveloper().getUsername(), postulation.getPublicationDate(), postulation.getStatus().toString());
                postulations.add(postulationDTO);
            }
            ProjectDTO projectDTO = new ProjectDTO(project.getIdProject(), project.getName(), project.getDescription(), project.getPresentation(), project.getRevision(), project.getStatus().toString(), project.getCategory(), project.getQualification(), project.getBudget(), project.getCreatedAt(), project.getUpdatedAt(), postulations);
            projects.add(projectDTO);
        }
        for(JobOffer offer : customer.getOffers()){
            PostulationDTO postulationDTO = new PostulationDTO(offer.getPostulation().getIdPostulation(), offer.getPostulation().getDeveloper().getUsername(), offer.getPostulation().getPublicationDate(), offer.getPostulation().getStatus().toString());
            JobOfferDTO jobOfferDTO = new JobOfferDTO(offer.getIdOffer(), offer.getBudget(), offer.getPublicationDate(), postulationDTO);
            offers.add(jobOfferDTO);
        }
        for(Developer developer: customer.getDevelopers()){
            DeveloperDTO developerDTO = developerService.returnDeveloperDTO(developer);
            developers.add(developerDTO);
        }
        return new CustomerDTO(customer.getUsername(), customer.getNames(), customer.getLastName(), customer.getEmail(), projects, offers, developers);
    }

    // GET CUSTOMER
    public Customer getCustomer(String username){
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if(customer.isEmpty()){
            throw new ResourceNotExistsException("Customer "+username+" has not been found");
        }
        return customer.get();
    }

    public CustomerDTO addFavorite(String customUser, String devUser) {
        Customer customer = getCustomer(customUser);
        customer.getDevelopers().add(developerService.getDeveloper(devUser));
        customerRepository.saveAndFlush(customer);
        return returnCustomerDTO(customer);
    }

    public CustomerDTO deleteFavorite(String customUser, String devUser) {
        Customer customer = getCustomer(customUser);
        customer.getDevelopers().remove((developerService.getDeveloper(devUser)));
        customerRepository.saveAndFlush(customer);
        return returnCustomerDTO(customer);
    }

    public void sendVerificationCode(EmailRequest request) {
        // Generar un código de verificación
        String code = String.valueOf((int) (Math.random() * 10000)); // Código de 4 dígitos
        verificationCodes.put(request.getEmail(), code);

        // Enviar correo electrónico (simulado)
        System.out.println("Enviando código " + code + " al correo " + request.getEmail());
        emailService.sendEmail(request.getEmail(), "Código de Verificación", "Atención su código para continuar con el cambio de su perfil es: "+ code);
    }

    public void verifyCode(VerificationRequest request) {
        String storedCode = verificationCodes.get(request.getEmail());

        if (storedCode == null || !storedCode.equals(request.getCode())) {
            throw new IllegalArgumentException("Código de verificación inválido o expirado");
        }

        // Código válido: elimina el código usado
        verificationCodes.remove(request.getEmail());
    }
}
