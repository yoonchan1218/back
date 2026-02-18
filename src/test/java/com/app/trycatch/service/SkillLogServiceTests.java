package com.app.trycatch.service;

import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.common.search.Search;
import com.app.trycatch.dto.skilllog.SkillLogDTO;
import com.app.trycatch.dto.skilllog.SkillLogWithPagingDTO;
import com.app.trycatch.dto.skilllog.TagDTO;
import com.app.trycatch.service.skilllog.SkillLogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

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

        skillLogDTO.setMemberId(2L);
        skillLogDTO.setSkillLogTitle("기술 블로그 테스트1");
        skillLogDTO.setSkillLogContent("내용1");

        // MockMultipartFile로 가짜 파일 생성
        List<MultipartFile> multipartFiles = new ArrayList<>();
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",                    // 파라미터명
                "test.png",                // 원본 파일명
                "image/png",               // Content-Type
                "테스트 이미지 내용".getBytes()  // 파일 내용 (아무 바이트나 OK)
        );
        multipartFiles.add(mockFile);

        skillLogService.write(skillLogDTO, multipartFiles);
    }

    @Test
    public void testRecentExperienceLogs() {
        Search search = new Search();
        log.info("{}", skillLogService.recentExperienceLogs(11L, 1, search));
    }

    @Test
    public void testAside() {
        log.info("{}", skillLogService.aside(9L));
    }

    @Test
    public void testList() {
        Search search = new Search();
        SkillLogWithPagingDTO skillLogWithPagingDTO = null;
        String[] tagNames = new String[1];

//        search.setKeyword("1");
//        tagNames[0] = "태그";
//        search.setTagNames(tagNames);
        search.setType("인기");

        skillLogWithPagingDTO = skillLogService.list(1, search);
        skillLogWithPagingDTO.getSkillLogs().forEach((skillLogDTO) -> {
            log.info("{}", skillLogDTO);
        });
    }

    @Test
    public void testDetail() {
        SkillLogDTO skillLogDTO = skillLogService.detail(30L);
        log.info("{}", skillLogDTO);
    }
}
