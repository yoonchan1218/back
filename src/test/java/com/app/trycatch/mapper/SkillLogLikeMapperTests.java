package com.app.trycatch.mapper;

import com.app.trycatch.domain.skilllog.SkillLogLikeVO;
import com.app.trycatch.domain.skilllog.SkillLogVO;
import com.app.trycatch.dto.skilllog.SkillLogLikeDTO;
import com.app.trycatch.mapper.skilllog.SkillLogLikeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class SkillLogLikeMapperTests {
    @Autowired
    private SkillLogLikeMapper skillLogLikeMapper;

    @Test
    public void testSelectCountBySkillLogId() {
        log.info("{}", skillLogLikeMapper.selectCountBySkillLogId(30L));
    }

    @Test
    public void testSelectLikedBySkillLogIdAndMemberId() {
        SkillLogLikeDTO skillLogLikeDTO = new SkillLogLikeDTO();
        skillLogLikeDTO.setSkillLogId(43L);
        skillLogLikeDTO.setMemberId(4L);
        log.info("{}", skillLogLikeMapper.selectBySkillLogIdAndMemberId(skillLogLikeDTO.toVO()).orElse(null));
    }

    @Test
    public void testInsert() {
        SkillLogLikeDTO skillLogLikeDTO = new SkillLogLikeDTO();
        skillLogLikeDTO.setSkillLogId(38L);
        skillLogLikeDTO.setMemberId(6L);

        skillLogLikeMapper.insert(skillLogLikeDTO.toVO());
    }

    @Test
    public void testDelete() {
        SkillLogLikeDTO skillLogLikeDTO = new SkillLogLikeDTO();
        skillLogLikeDTO.setSkillLogId(38L);
        skillLogLikeDTO.setMemberId(6L);

        SkillLogLikeVO skillLogLikeVO = skillLogLikeMapper.selectBySkillLogIdAndMemberId(skillLogLikeDTO.toVO()).orElse(null);

        skillLogLikeMapper.delete(skillLogLikeVO.getId());
    }
}
