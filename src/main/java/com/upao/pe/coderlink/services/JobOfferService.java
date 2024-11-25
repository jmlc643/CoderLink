package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.offer.CreateJobOfferRequest;
import com.upao.pe.coderlink.dtos.offer.EditJobOfferRequest;
import com.upao.pe.coderlink.dtos.offer.JobOfferDTO;
import com.upao.pe.coderlink.dtos.postulation.PostulationDTO;
import com.upao.pe.coderlink.exceptions.ResourceNotExistsException;
import com.upao.pe.coderlink.models.Customer;
import com.upao.pe.coderlink.models.JobOffer;
import com.upao.pe.coderlink.models.Postulation;
import com.upao.pe.coderlink.models.enums.PostulationStatus;
import com.upao.pe.coderlink.repos.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired private PostulationService postulationService;

    // CREATE
    public JobOfferDTO createJobOffer(CreateJobOfferRequest request){
        Customer customer = customerService.getCustomer(request.getCustomerUsername());
        Postulation postulation = postulationService.getPostulation(request.getPostulationId());
        postulation.setStatus(PostulationStatus.OFFER);
        postulationService.saveChanges(postulation);
        JobOffer jobOffer = new JobOffer(null, request.getBudget(), LocalDateTime.now(), customer, postulation, null);
        return returnJobOfferDTO(jobOfferRepository.save(jobOffer));
    }

    // READ
    public List<JobOfferDTO> listJobOffers(){
        return jobOfferRepository.findAll().stream().map(this::returnJobOfferDTO).toList();
    }

    // UPDATE
    public JobOfferDTO editJobOffer(EditJobOfferRequest request){
        JobOffer jobOffer = getJobOffer(request.getId());
        jobOffer.setBudget(jobOffer.getBudget());
        jobOfferRepository.saveAndFlush(jobOffer);
        return returnJobOfferDTO(jobOffer);
    }

    // DELETE
    public List<JobOfferDTO> deleteJobOffer(Long id){
        jobOfferRepository.deleteById(id);
        return listJobOffers();
    }

    // DTO
    public JobOfferDTO returnJobOfferDTO(JobOffer jobOffer){
        PostulationDTO postulationDTO = new PostulationDTO(jobOffer.getPostulation().getIdPostulation(), jobOffer.getPostulation().getDeveloper().getUsername(), jobOffer.getPostulation().getPublicationDate(), jobOffer.getPostulation().getStatus().toString());
        return new JobOfferDTO(jobOffer.getIdOffer(), jobOffer.getBudget(), jobOffer.getPublicationDate(), postulationDTO);
    }

    public JobOffer getJobOffer(Long id){
        Optional<JobOffer> offer = jobOfferRepository.findById(id);
        if(offer.isEmpty()){
            throw new ResourceNotExistsException("This offer does not exists");
        }
        return offer.get();
    }
}
