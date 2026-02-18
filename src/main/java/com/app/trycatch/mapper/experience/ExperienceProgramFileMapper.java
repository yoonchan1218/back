package com.app.trycatch.mapper.experience;

import com.app.trycatch.dto.experience.ExperienceProgramFileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExperienceProgramFileMapper {
    public List<ExperienceProgramFileDTO> selectAllByExperienceProgramId(Long id);
}
