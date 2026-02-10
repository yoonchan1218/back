package com.app.trycatch.service.skilllog;

import com.app.trycatch.domain.skilllog.SkillLogTagVO;
import com.app.trycatch.domain.skilllog.SkillLogVO;
import com.app.trycatch.domain.skilllog.TagVO;
import com.app.trycatch.dto.skilllog.SkillLogDTO;
import com.app.trycatch.dto.skilllog.TagDTO;
import com.app.trycatch.repository.skilllog.SkillLogDAO;
import com.app.trycatch.repository.skilllog.SkillLogTagDAO;
import com.app.trycatch.repository.skilllog.TagDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SkillLogService {
    private final SkillLogDAO skillLogDAO;
    private final TagDAO tagDAO;
    private final SkillLogTagDAO skillLogTagDAO;

    public void write(SkillLogDTO skillLogDTO) {
        SkillLogVO skillLogVO = skillLogDTO.toSkillLogVO();
        SkillLogTagVO skillLogTagVO = null;

//       tbl_skill_log_tag 테이블에 insert 하기 위해 skillLogId와 tagId가 필요하다.
        Long skillLogId = null;
        Long tagId = null;

//        skillLog insert
        skillLogDAO.save(skillLogVO);
        skillLogId = skillLogVO.getId();

//        tag insert
        for (TagDTO tag:skillLogDTO.getTags()) {
//            DB에 중복된 태그가 있는지 검사한다.
            Optional<TagVO> foundTag = tagDAO.findByTagName(tag.getTagName());

            if(foundTag.isEmpty()) {
//                사용자가 입력한 태그 중에 (db에) 중복된 태그가 없다면
//                DB에 insert 후, tag의 id를 가져온다.
                TagVO tagVO = tag.toVO();
                tagDAO.save(tagVO);
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

//            skillLogTag(중간 테이블) insert
            skillLogTagDAO.save(skillLogTagVO);
        }
    }
}
