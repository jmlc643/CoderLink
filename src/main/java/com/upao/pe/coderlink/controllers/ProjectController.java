package com.upao.pe.coderlink.controllers;

import com.upao.pe.coderlink.dtos.project.CreateProjectRequest;
import com.upao.pe.coderlink.dtos.project.ProjectDTO;
import com.upao.pe.coderlink.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
}
