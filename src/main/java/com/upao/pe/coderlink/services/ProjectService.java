package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.joboffer.JobOfferDTO;
import com.upao.pe.coderlink.dtos.project.CreateProjectRequest;
import com.upao.pe.coderlink.dtos.project.ProjectDTO;
import com.upao.pe.coderlink.dtos.skill.CreateSkillRequest;
import com.upao.pe.coderlink.dtos.skill.SkillDTO;
import com.upao.pe.coderlink.models.JobOffer;
import com.upao.pe.coderlink.models.Project;
import com.upao.pe.coderlink.models.Skill;
import com.upao.pe.coderlink.repos.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

}
