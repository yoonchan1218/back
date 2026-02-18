package com.app.trycatch.mapper;

import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.common.search.Search;
import com.app.trycatch.dto.experience.ExperienceProgramDTO;
import com.app.trycatch.mapper.experience.ExperienceProgramMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class ExperienceProgramMapperTests {
    @Autowired
    private ExperienceProgramMapper experienceProgramMapper;

    @Test
    public void testSelectAllByChallengerMemberId() {
        Search search = new Search();
        Long memberId = 11L;

        search.setKeyword("");

        int total = experienceProgramMapper.selectTotalByMemberIdOfChallenger(search, memberId);
        Criteria criteria = new Criteria(1, total);

        List<ExperienceProgramDTO> experiencePrograms =
                experienceProgramMapper.selectAllByMemberIdOfChallenger(criteria, search, memberId);

        experiencePrograms.forEach((experienceProgram) -> log.info("{}", experienceProgram));
    }

    @Test
    public void testSelectById() {
        log.info("{}", experienceProgramMapper.selectById(1L).orElse(null));
    }
}
