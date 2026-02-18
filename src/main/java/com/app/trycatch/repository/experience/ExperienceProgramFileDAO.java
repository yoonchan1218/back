package com.app.trycatch.repository.experience;

import com.app.trycatch.dto.experience.ExperienceProgramDTO;
import com.app.trycatch.dto.experience.ExperienceProgramFileDTO;
import com.app.trycatch.mapper.experience.ExperienceProgramFileMapper;
import com.app.trycatch.mapper.experience.ExperienceProgramMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExperienceProgramFileDAO {
    private final ExperienceProgramFileMapper experienceProgramFileMapper;

    public List<ExperienceProgramFileDTO> findAllByExperienceProgramId (Long id) {
        return experienceProgramFileMapper.selectAllByExperienceProgramId(id);
    }
}
