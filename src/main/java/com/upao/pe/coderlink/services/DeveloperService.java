package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.customer.EmailRequest;
import com.upao.pe.coderlink.dtos.customer.VerificationRequest;
import com.upao.pe.coderlink.dtos.developer.CreateDeveloperRequest;
import com.upao.pe.coderlink.dtos.developer.DeveloperDTO;
import com.upao.pe.coderlink.dtos.developer.UpdateDeveloperRequest;
import com.upao.pe.coderlink.dtos.postulation.PostulationDTO;
import com.upao.pe.coderlink.dtos.skill.CreateSkillRequest;
import com.upao.pe.coderlink.dtos.skill.SkillDTO;
import com.upao.pe.coderlink.exceptions.ResourceExistsException;
import com.upao.pe.coderlink.exceptions.ResourceNotExistsException;
import com.upao.pe.coderlink.models.Developer;
import com.upao.pe.coderlink.models.Postulation;
import com.upao.pe.coderlink.models.Skill;
import com.upao.pe.coderlink.models.enums.TypeUser;
import com.upao.pe.coderlink.repos.DeveloperRepository;
import com.upao.pe.coderlink.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired private SkillService skillService;

    @Autowired private EmailService emailService;

    @Autowired private PasswordEncoder passwordEncoder;

    // CREATE
    public Developer createDeveloper(CreateDeveloperRequest request){
        List<Skill> skills = new ArrayList<>();
        request.getSkills().forEach(skill -> {
            Skill skillToList;
            if(skillService.getSkill(skill) != null){
                skillToList = skillService.getSkill(skill);
            } else{
                skillToList = skillService.createSkill(new CreateSkillRequest(skill));
            }
            skills.add(skillToList);
        });
        Developer developer = new Developer(request.getPortfolio(), request.getPaymentRate(), request.getWorkExperience(), new ArrayList<>(), skills);
        if(userRepository.existsUserByUsername(developer.getUsername())){
            throw new ResourceExistsException("User "+developer.getUsername()+" exists");
        }
        if(userRepository.existsUserByEmail(developer.getEmail())){
            throw new ResourceExistsException("User with email "+developer.getEmail()+" exists");
        }
        developer.setUsername(request.getUsername());
        developer.setNames(request.getNames());
        developer.setLastName(request.getLastName());
        developer.setEmail(request.getEmail());
        developer.setPassword(request.getPassword());
        developer.setTypeUser(TypeUser.DEVELOPER);
        return developerRepository.save(developer);
    }

    // READ
    public List<DeveloperDTO> listDevelopers(){return developerRepository.findAll().stream().map(this::returnDeveloperDTO).toList();}

    // UPDATE
    public DeveloperDTO updateDeveloper(UpdateDeveloperRequest request, String username){
        Developer developer = getDeveloper(username);
        developer.setUsername(request.getUsername());
        developer.setPortafolio(request.getPortfolio());
        developer.setPaymentRate(request.getPaymentRate());
        developer.setEmail(request.getEmail());
        developer.setPassword(passwordEncoder.encode(request.getPassword()));
        developerRepository.saveAndFlush(developer);
        return returnDeveloperDTO(developerRepository.saveAndFlush(developer));
    }

    // DELETE

    // DTO
    public DeveloperDTO returnDeveloperDTO(Developer developer){
        List<PostulationDTO> postulations = new ArrayList<>();
        List<SkillDTO> skills = new ArrayList<>();
        for(Postulation postulation : developer.getPostulations()){
            PostulationDTO postulationDTO = new PostulationDTO(postulation.getIdPostulation(), postulation.getDeveloper().getUsername(), postulation.getPublicationDate(), postulation.getStatus().toString());
            postulations.add(postulationDTO);
        }
        for(Skill skill : developer.getSkills()){
            SkillDTO skillDTO = skillService.returnSkillDTO(skill);
            skills.add(skillDTO);
        }
        return new DeveloperDTO(developer.getUsername(), developer.getNames(), developer.getLastName(), developer.getEmail(), developer.getPortafolio(), developer.getPaymentRate(), developer.getWorkExperience(), postulations, skills);
    }

    // GET
    public Developer getDeveloper(String username){
        Optional<Developer> developer = developerRepository.findByUsername(username);
        if(developer.isEmpty()){
            throw new ResourceNotExistsException("Developer "+username+" has not been founded");
        }
        return developer.get();
    }

    public List<DeveloperDTO> filterBySkills(List<String> names) {
        List<Skill> skills = skillService.getSkills(names);

        if (skills == null || skills.isEmpty()) {
            return List.of();
        }

        List<Developer> results = developerRepository.findAll().stream()
                .filter(dev -> dev.getSkills().stream().anyMatch(skills::contains))
                .toList();

        return results.stream().map(this::returnDeveloperDTO).toList();
    }
    public void sendVerificationCode(EmailRequest request) {
        // Generar un código de verificación
        String code = String.valueOf((int) (Math.random() * 10000)); // Código de 4 dígitos
        verificationCodes.put(request.getEmail(), code);

        // Enviar correo electrónico (simulado)
        System.out.println("Enviando código " + code + " al correo " + request.getEmail());
        emailService.sendEmail(request.getEmail(), "Código de Verificación", "Atención su código para continuar con el cambio de su perfil es: "+ code);
    }

    public void verifyCode(VerificationRequest request) {
        String storedCode = verificationCodes.get(request.getEmail());

        if (storedCode == null || !storedCode.equals(request.getCode())) {
            throw new IllegalArgumentException("Código de verificación inválido o expirado");
        }

        // Código válido: elimina el código usado
        verificationCodes.remove(request.getEmail());
    }
}
