package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.skill.CreateSkillRequest;
import com.upao.pe.coderlink.dtos.skill.SkillDTO;
import com.upao.pe.coderlink.models.Skill;
import com.upao.pe.coderlink.repos.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    @Autowired private SkillRepository skillRepository;

    public Skill createSkill(CreateSkillRequest request){
        Skill skill = new Skill(null, request.getName());
        return skillRepository.save(skill);
    }

    public SkillDTO returnSkillDTO(Skill skill){
        return new SkillDTO(skill.getName());
    }

    public Skill getSkill(String name){
        Optional<Skill> skill = skillRepository.findByName(name);
        return skill.orElse(null);
    }

    public List<Skill> getSkills(List<String> names){
        return names.isEmpty() ? null : skillRepository.findByNameIn(names);
    }
}
