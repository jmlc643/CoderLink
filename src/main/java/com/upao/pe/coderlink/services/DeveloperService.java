package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.developer.CreateDeveloperRequest;
import com.upao.pe.coderlink.dtos.developer.DeveloperDTO;
import com.upao.pe.coderlink.dtos.postulation.PostulationDTO;
import com.upao.pe.coderlink.exceptions.ResourceNotExistsException;
import com.upao.pe.coderlink.models.Developer;
import com.upao.pe.coderlink.models.Postulation;
import com.upao.pe.coderlink.models.enums.TypeUser;
import com.upao.pe.coderlink.repos.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeveloperService {

    @Autowired
    DeveloperRepository developerRepository;

    // CREATE
    public DeveloperDTO createDeveloper(CreateDeveloperRequest request){
        Developer developer = new Developer(request.getPortfolio(), request.getHoursWorked(), request.getPaymentRate(), request.getWorkExperience(), request.getYearsExperience(), new ArrayList<>());
        developer.setDni(request.getDni());
        developer.setNames(request.getNames());
        developer.setLastName(request.getLastName());
        developer.setEmail(request.getEmail());
        developer.setPassword(request.getPassword());
        developer.setTypeUser(TypeUser.valueOf(request.getTypeUser()));
        return returnDeveloperDTO(developerRepository.save(developer));
    }

    // READ
    public List<DeveloperDTO> listDevelopers(){return developerRepository.findAll().stream().map(this::returnDeveloperDTO).toList();}

    // UPDATE

    // DELETE

    // DTO
    public DeveloperDTO returnDeveloperDTO(Developer developer){
        List<PostulationDTO> postulations = new ArrayList<>();
        for(Postulation postulation : developer.getPostulations()){
            PostulationDTO postulationDTO = new PostulationDTO(postulation.getPublicationDate(), postulation.getStatus());
            postulations.add(postulationDTO);
        }
        return new DeveloperDTO(developer.getNames(), developer.getLastName(), developer.getEmail(), developer.getPortafolio(), developer.getHoursWorked(), developer.getPaymentRate(), developer.getWorkExperience(), developer.getYearsExperience(), postulations);
    }

    // GET
    public Developer getDeveloper(String names, String lastName){
        Optional<Developer> developer = developerRepository.findByNamesAndLastName(names, lastName);
        if(developer.isEmpty()){
            throw new ResourceNotExistsException("Developer "+names+" "+lastName+" has not been founded");
        }
        return developer.get();
    }
}
