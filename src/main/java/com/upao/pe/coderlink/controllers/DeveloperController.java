package com.upao.pe.coderlink.controllers;

import com.upao.pe.coderlink.dtos.developer.DeveloperDTO;
import com.upao.pe.coderlink.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("developer")
public class DeveloperController {
    @Autowired private DeveloperService developerService;

    @GetMapping("/list/")
    public List<DeveloperDTO> listDevelopers(){return developerService.listDevelopers();}

    @GetMapping("/get-developer/{username}")
    public DeveloperDTO getDeveloper(@PathVariable String username){
        return developerService.returnDeveloperDTO(developerService.getDeveloper(username));
    }
}
