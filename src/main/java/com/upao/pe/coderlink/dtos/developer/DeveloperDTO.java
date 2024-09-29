package com.upao.pe.coderlink.dtos.developer;

import com.upao.pe.coderlink.dtos.postulation.PostulationDTO;
import com.upao.pe.coderlink.dtos.skill.SkillDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DeveloperDTO {
    private String username;
    private String names;
    private String lastNames;
    private String email;
    private String portfolio;
    private String paymentRate;
    private String workExperience;
    private List<PostulationDTO> postulations;
    private List<SkillDTO> skills;
}
