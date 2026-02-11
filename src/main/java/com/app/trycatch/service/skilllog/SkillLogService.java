package com.app.trycatch.service.skilllog;

import com.app.trycatch.domain.skilllog.SkillLogVO;
import com.app.trycatch.domain.skilllog.TagVO;
import com.app.trycatch.dto.skilllog.SkillLogDTO;
import com.app.trycatch.dto.skilllog.TagDTO;
import com.app.trycatch.repository.skilllog.SkillLogDAO;
import com.app.trycatch.repository.skilllog.TagDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SkillLogService {
    private final SkillLogDAO skillLogDAO;
    private final TagDAO tagDAO;

    public void write(SkillLogDTO skillLogDTO) {
       skillLogDAO.save(skillLogDTO);
       skillLogDTO.getTags().forEach((tagDTO) -> {
           tagDTO.setSkillLogId(skillLogDTO.getId());
           tagDAO.save(tagDTO.toVO());
       });
    }
}
