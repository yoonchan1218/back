package com.app.trycatch.mapper;

import com.app.trycatch.domain.skilllog.SkillLogVO;
import com.app.trycatch.dto.skilllog.SkillLogDTO;
import com.app.trycatch.mapper.skilllog.SkillLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class SkillLogMapperTests {
    @Autowired
    private SkillLogMapper skillLogMapper;

    @Test
    public void testInsert() {
        SkillLogDTO skillLogDTO = new SkillLogDTO();

        skillLogDTO.setMemberId(1L);
        skillLogDTO.setExperienceProgramId(2L);
        skillLogDTO.setSkillLogTitle("기술 블로그 테스트1");
        skillLogDTO.setSkillLogContent("내용1");

        skillLogMapper.insert(skillLogDTO);

        log.info("{}................", skillLogDTO.getId());

    }
}
