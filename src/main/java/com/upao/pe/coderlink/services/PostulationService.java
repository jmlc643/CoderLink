package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.postulation.CreatePostulationRequest;
import com.upao.pe.coderlink.dtos.postulation.PostulationDTO;
import com.upao.pe.coderlink.exceptions.ResourceNotExistsException;
import com.upao.pe.coderlink.models.Developer;
import com.upao.pe.coderlink.models.Postulation;
import com.upao.pe.coderlink.models.Project;
import com.upao.pe.coderlink.models.enums.PostulationStatus;
import com.upao.pe.coderlink.repos.PostulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostulationService {
    @Autowired
    private PostulationRepository postulationRepository;

    @Autowired private ProjectService projectService;

    @Autowired private DeveloperService developerService;

    // CREATE
    public PostulationDTO createPostulation(CreatePostulationRequest request){
        Developer developer = developerService.getDeveloper(request.getDevName());
        Project project = projectService.getProjectById(request.getIdProject());
        Postulation postulation = new Postulation(null, LocalDateTime.now(), PostulationStatus.SENDED, developer, project, null);
        return returnPostulationDTO(postulationRepository.save(postulation));
    }

    // READ
    public List<PostulationDTO> listPostulations(){
        return postulationRepository.findAll().stream().map(this::returnPostulationDTO).toList();
    }

    // DTO
    public PostulationDTO returnPostulationDTO(Postulation postulation){
        return new PostulationDTO(postulation.getIdPostulation(), postulation.getDeveloper().getUsername(), postulation.getPublicationDate(), postulation.getStatus().toString());
    }

    // GET
    public Postulation getPostulation(Long id){
        Optional<Postulation> postulation = postulationRepository.findById(id);
        if(postulation.isEmpty()){
            throw new ResourceNotExistsException("This postulation does not exists");
        }
        return postulation.get();
    }

    public void saveChanges(Postulation postulation){
        postulationRepository.saveAndFlush(postulation);
    }
}
