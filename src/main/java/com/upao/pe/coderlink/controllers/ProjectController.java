package com.upao.pe.coderlink.controllers;

import com.upao.pe.coderlink.dtos.project.CreateProjectRequest;
import com.upao.pe.coderlink.dtos.project.ProjectDTO;
import com.upao.pe.coderlink.dtos.project.SearchProjectRequest;
import com.upao.pe.coderlink.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController {
    @Autowired
    ProjectService projectService;

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
    public ResponseEntity<List<ProjectDTO>> searchProject(@Valid @RequestBody SearchProjectRequest request){
        return new ResponseEntity<>(projectService.getProjectsByName(request.getProjectName()), HttpStatus.FOUND);
    }
}
