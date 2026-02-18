package com.app.trycatch.mapper.skilllog;

import com.app.trycatch.common.search.Search;
import com.app.trycatch.domain.skilllog.SkillLogLikeVO;
import com.app.trycatch.domain.skilllog.SkillLogVO;
import com.app.trycatch.dto.skilllog.SkillLogDTO;
import com.app.trycatch.dto.skilllog.SkillLogLikeDTO;
import com.app.trycatch.repository.skilllog.SkillLogLikeDAO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface SkillLogLikeMapper {
//    좋아요 개수
    public int selectCountBySkillLogId(Long id);

//    조회
    public Optional<SkillLogLikeVO> selectBySkillLogIdAndMemberId(SkillLogLikeVO skillLogLikeVO);

//    추가
    public void insert(SkillLogLikeVO skillLogLikeVO);
//    삭제
    public void delete(Long id);
}
