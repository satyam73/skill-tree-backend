package com.RDS.skilltree.Skill;

import com.RDS.skilltree.User.UserModel;
import com.RDS.skilltree.User.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SkillsServiceImpl implements SkillsService{
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    public SkillsServiceImpl(SkillRepository skillRepository,
                             UserRepository userRepository){
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public SkillDTO getSkillById(UUID id){
        Optional<SkillModel> skillModel = skillRepository.findById(id);
        return skillModel.map(SkillDTO::toDto).orElse(null);
    }

    @Override
    public SkillDTO  getSkillByName(String skillName){
        Optional<SkillModel> skillModel = skillRepository.findByName(skillName);
        return skillModel.map(SkillDTO::toDto).orElse(null);
    }

    @Override
    public Page<SkillDTO> getAllSkills(Pageable pageable){
        Page<SkillModel> skillModels = skillRepository.findAll(pageable);
        return skillModels.map(SkillDTO::toDto);
    }

    @Override
    public String createSkill(SkillDRO skillDRO){
        SkillModel newSkill = SkillDRO.toModel(skillDRO);
        newSkill.setCreatedAt(Instant.now());
        newSkill.setUpdatedAt(Instant.now());
        UserModel user = userRepository.findById(skillDRO.getCreatedBy()).get();
        newSkill.setUpdatedBy(user);
        newSkill.setCreatedBy(user);
        try {
            skillRepository.save(newSkill);
        } catch(DataIntegrityViolationException ex){
            log.error("Error saving the skills object with name : {}, with exception :{}", skillDRO.getName(), ex.getMessage(), ex);
            throw ex;
        }
        return "Success";
    }
}
