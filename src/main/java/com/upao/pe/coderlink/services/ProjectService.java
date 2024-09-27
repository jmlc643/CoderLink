package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.joboffer.JobOfferDTO;
import com.upao.pe.coderlink.dtos.project.CreateProjectRequest;
import com.upao.pe.coderlink.dtos.project.ProjectDTO;
import com.upao.pe.coderlink.dtos.skill.CreateSkillRequest;
import com.upao.pe.coderlink.dtos.skill.SkillDTO;
import com.upao.pe.coderlink.exceptions.ResourceNotExistsException;
import com.upao.pe.coderlink.models.JobOffer;
import com.upao.pe.coderlink.models.Project;
import com.upao.pe.coderlink.models.Skill;
import com.upao.pe.coderlink.repos.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired SkillService skillService;

    // CREATE
    public ProjectDTO createProject(CreateProjectRequest request){
        request.getSkills().forEach(skill -> {
            CreateSkillRequest skillRequest = new CreateSkillRequest(skill.getName(), skill.getDescription());
            skillService.createSkill(skillRequest);
        });
        Project project = new Project(null, request.getName(), request.getDescription(), request.getMilestones(), request.getPresentation(), request.getRevision(), request.getStatus(), request.getCategory(), request.getQualification(), LocalDate.now(), null, new ArrayList<>(), request.getSkills());
        return returnProjectDTO(projectRepository.save(project));
    }

    // READ
    public List<ProjectDTO> listProjects(){return projectRepository.findAll().stream().map(this::returnProjectDTO).toList();}

    // DELETE
    public List<ProjectDTO> deleteProject(Long id){
        Project project = getProjectById(id);
        projectRepository.delete(project);
        return listProjects();
    }

    // DTO
    public ProjectDTO returnProjectDTO(Project project) {
        List<SkillDTO> skills = new ArrayList<>();
        List<JobOfferDTO> offers = new ArrayList<>();
        for(JobOffer jobOffer : project.getJobOffers()) {
            JobOfferDTO jobOfferDTO = new JobOfferDTO(jobOffer.getDescription(), jobOffer.getBudget(), jobOffer.getDuration(), jobOffer.getPublicationDate());
            offers.add(jobOfferDTO);
        }
        for(Skill skill : project.getSkills()) {
            SkillDTO skillDTO = new SkillDTO(skill.getName(), skill.getDescription());
            skills.add(skillDTO);
        }
        return new ProjectDTO(project.getName(), project.getDescription(), project.getMilestones(), project.getPresentation(), project.getRevision(), project.getStatus(), project.getCategory(), project.getQualification(), project.getCreatedAt(), offers, skills);
    }

    // Search
    public List<ProjectDTO> getProjectsByName(String name){
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        Optional<List<Project>> projects = projectRepository.findAllByNameContaining(name);
        if(projects.isEmpty()){
            throw new ResourceNotExistsException("No project has not been founded");
        }
        projects.get().forEach(project -> {
            projectDTOS.add(returnProjectDTO(project));
        });
        return projectDTOS;
    }

    public Project getProjectById(Long id){
        Optional<Project> project = projectRepository.findById(id);
        if(project.isEmpty()){
            throw new ResourceNotExistsException("This project has not been founded");
        }
        return project.get();
    }

}
