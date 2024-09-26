package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.skill.CreateSkillRequest;
import com.upao.pe.coderlink.dtos.skill.SkillDTO;
import com.upao.pe.coderlink.models.Skill;
import com.upao.pe.coderlink.repos.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillService {

    @Autowired private SkillRepository skillRepository;

    public void createSkill(CreateSkillRequest request){
        new Skill(null, request.getName(), request.getDescription());
    }

    public SkillDTO returnSkillDTO(Skill skill){
        return new SkillDTO(skill.getName(), skill.getDescription());
    }
}
