package com.upao.pe.coderlink.controllers;

import com.upao.pe.coderlink.dtos.project.*;
import com.upao.pe.coderlink.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/list/")
    public List<ProjectDTO> listProjects(){
        return projectService.listProjects();
    }

    @PostMapping("/create/")
    public ProjectDTO createProject(@Valid @RequestBody CreateProjectRequest request){
        return projectService.createProject(request);
    }

    @DeleteMapping("/delete/{id}")
    public List<ProjectDTO> deleteProject(@PathVariable Long id){
        return projectService.deleteProject(id);
    }

    @PostMapping("/search/")
    public List<ProjectDTO> searchProject(@Valid @RequestBody SearchProjectRequest request) {
        return (projectService.getProjectsByName(request.getProjectName()));
    }

    @GetMapping("/get-project/{id}")
    public ProjectDTO getProject(@PathVariable Long id){
        return projectService.returnProjectDTO(projectService.getProjectById(id));
    }

    @PostMapping("/set-status/")
    public ProjectDTO setStatusProject(@RequestBody SetStatusRequest request){
        return projectService.setStatusProject(request);
    }

    @PostMapping("/ask-changes/")
    public AskChangesResponse askChanges(@RequestBody AskChangesRequest request){
        return projectService.askChanges(request);
    }
}
