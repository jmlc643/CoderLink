package com.upao.pe.coderlink.controllers;

import com.upao.pe.coderlink.dtos.customer.EmailRequest;
import com.upao.pe.coderlink.dtos.customer.VerificationRequest;
import com.upao.pe.coderlink.dtos.developer.DeveloperDTO;
import com.upao.pe.coderlink.dtos.developer.UpdateDeveloperRequest;
import com.upao.pe.coderlink.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/filter-developers/")
    public List<DeveloperDTO> filterDevelopersBySkills(@RequestBody List<String> names){
        return developerService.filterBySkills(names);
    }

    @PutMapping("/edit-developer/{username}")
    public DeveloperDTO updateDeveloper(@RequestBody UpdateDeveloperRequest request, @PathVariable String username){
        return developerService.updateDeveloper(request, username);
    }

    @PostMapping("/send-code/")
    public ResponseEntity<Void> sendVerificationCode(@RequestBody EmailRequest request) {
        developerService.sendVerificationCode(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-code/")
    public ResponseEntity<Void> verifyEmailCode(@RequestBody VerificationRequest request) {
        developerService.verifyCode(request);
        return ResponseEntity.ok().build();
    }
}
