package com.app.trycatch.mapper;

import com.app.trycatch.domain.skilllog.SkillLogTagVO;
import com.app.trycatch.domain.skilllog.SkillLogVO;
import com.app.trycatch.domain.skilllog.TagVO;
import com.app.trycatch.dto.skilllog.SkillLogDTO;
import com.app.trycatch.dto.skilllog.TagDTO;
import com.app.trycatch.mapper.skilllog.SkillLogMapper;
import com.app.trycatch.mapper.skilllog.SkillLogTagMapper;
import com.app.trycatch.mapper.skilllog.TagMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Slf4j
public class SkillLogTagMapperTests {
    @Autowired
    private SkillLogTagMapper skillLogTagMapper;
    @Autowired
    private SkillLogMapper skillLogMapper;
    @Autowired
    private TagMapper tagMapper;


    @Test
    public void testInsert() {
        SkillLogTagVO skillLogTagVO = null;

        SkillLogDTO skillLogDTO = new SkillLogDTO();
        SkillLogVO skillLogVO = null;
        Long skillLogId = null;
        
        List<TagDTO> tags = new ArrayList<>();
        IntStream.range(1, 5).forEach((index) -> {
            TagDTO tagDTO = new TagDTO();
            tagDTO.setTagName("태그" + index);

            tags.add(tagDTO);
        });
        skillLogDTO.setTags(tags);

//        skillLogDTO에도 값을 설정한다.
        skillLogDTO.setMemberId(1L);
        skillLogDTO.setExperienceProgramId(2L);
        skillLogDTO.setSkillLogTitle("기술 블로그 테스트4");
        skillLogDTO.setSkillLogContent("내용4");

//       tbl_skill_log_tag 테이블에 insert 하기 위해 skillLogId가 필요하기 때문에
//       VO를 변수에 담아서 skillLogVO.getId()로 id를 가져올 수 있게 한다.
        skillLogVO = skillLogDTO.toSkillLogVO();

//        skillLog insert
        skillLogMapper.insert(skillLogVO);
        skillLogId = skillLogVO.getId();

//        tag insert
        for (TagDTO tag:skillLogDTO.getTags()) {
//            DB에 중복된 태그가 있는지 검사한다.
            Optional<TagVO> foundTag = tagMapper.selectByTagName(tag.getTagName());
            Long tagId = null;

            if(foundTag.isEmpty()) {
//                사용자가 입력한 태그 중에 (db에) 중복된 태그가 없다면
//                DB에 insert 후, tag의 id를 가져온다.
                TagVO tagVO = tag.toVO();
                tagMapper.insert(tagVO);
                tagId = tagVO.getId();

            } else {
//                이미 DB에 존재하는 태그라면 foundTag의 id를 가져온다.
                tagId = foundTag.get().getId();
            }

//            skillLogTagVO에 넣을 값을 설정한다.
            skillLogTagVO = SkillLogTagVO.builder()
                    .skillLogId(skillLogId)
                    .tagId(tagId)
                    .build();

//            중간 테이블 insert
            skillLogTagMapper.insert(skillLogTagVO);
        }
    }
}
