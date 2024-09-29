package com.upao.pe.coderlink.controllers;

import com.upao.pe.coderlink.dtos.postulation.CreatePostulationRequest;
import com.upao.pe.coderlink.dtos.postulation.PostulationDTO;
import com.upao.pe.coderlink.services.PostulationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("postulation")
public class PostulationController {

    @Autowired
    private PostulationService postulationService;

    @GetMapping("/list/")
    public List<PostulationDTO> listPostulations(){
        return postulationService.listPostulations();
    }

    @PostMapping("/create/")
    public PostulationDTO createPostulation(@Valid @RequestBody CreatePostulationRequest request){
        return postulationService.createPostulation(request);
    }

    @GetMapping("/get-postulation/{id}")
    public PostulationDTO getPostulation(@PathVariable Long id){
        return postulationService.returnPostulationDTO(postulationService.getPostulation(id));
    }
}
