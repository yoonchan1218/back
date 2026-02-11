package com.app.trycatch.service;

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

        List<TagDTO> tags = new ArrayList<>();
        IntStream.range(1, 3).forEach((index) -> {
            TagDTO tagDTO = new TagDTO();
            tagDTO.setTagName("태그" + index);

            tags.add(tagDTO);
        });
        skillLogDTO.setTags(tags);

        skillLogDTO.setMemberId(1L);
        skillLogDTO.setSkillLogTitle("기술 블로그 테스트2");
        skillLogDTO.setSkillLogContent("내용2");

        skillLogService.write(skillLogDTO);
    }
}
