package com.app.trycatch.service;

import com.app.trycatch.domain.skilllog.SkillLogTagVO;
import com.app.trycatch.domain.skilllog.SkillLogVO;
import com.app.trycatch.dto.skilllog.SkillLogDTO;
import com.app.trycatch.dto.skilllog.TagDTO;
import com.app.trycatch.service.skilllog.SkillLogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Slf4j
public class SkillLogServiceTests {
    @Autowired
    private SkillLogService skillLogService;

    @Test
    public void testWrite() {
        SkillLogDTO skillLogDTO = new SkillLogDTO();

//        화면에서 받아온 값이 없으니, 직접 tags에 값을 넣어준다.
        List<TagDTO> tags = new ArrayList<>();
        IntStream.range(4, 6).forEach((index) -> {
            TagDTO tagDTO = new TagDTO();
            tagDTO.setTagName("태그" + index);

            tags.add(tagDTO);
        });
        skillLogDTO.setTags(tags);

//        skillLogDTO에도 값을 설정한다.
        skillLogDTO.setMemberId(1L);
//        skillLogDTO.setExperienceProgramId(2L);
        skillLogDTO.setSkillLogTitle("기술 블로그 테스트5");
        skillLogDTO.setSkillLogContent("내용5");

        skillLogService.write(skillLogDTO);
    }
}
