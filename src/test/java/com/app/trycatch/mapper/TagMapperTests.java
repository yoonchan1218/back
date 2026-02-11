package com.app.trycatch.mapper;

import com.app.trycatch.domain.skilllog.TagVO;
import com.app.trycatch.dto.skilllog.TagDTO;
import com.app.trycatch.mapper.skilllog.TagMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Slf4j
public class TagMapperTests {
    @Autowired
    private TagMapper tagMapper;

    @Test
    public void testInert() {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setTagName("태그1");
        tagDTO.setSkillLogId(8L);
        tagMapper.insert(tagDTO.toVO());
    }

}
