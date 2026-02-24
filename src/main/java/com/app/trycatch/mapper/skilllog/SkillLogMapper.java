package com.app.trycatch.mapper.skilllog;

import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.common.search.Search;
import com.app.trycatch.domain.skilllog.SkillLogVO;
import com.app.trycatch.dto.skilllog.SkillLogAsideDTO;
import com.app.trycatch.dto.skilllog.SkillLogDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface SkillLogMapper {
//    추가
    public void insert(SkillLogDTO skillLogDTO);
//    aside
    public SkillLogAsideDTO selectProfileByMemberId(Long id);

//    목록
    public List<SkillLogDTO> selectAll(@Param("criteria") Criteria criteria, @Param("search") Search search);
//    전체 개수
    public int selectTotal(@Param("search") Search search);

//    조회
    public Optional<SkillLogDTO> selectById(Long id);
//    조회수 증가
    public void updateSkillLogViewCount(Long id);

//    수정
    public void update(SkillLogVO skillLogVO);

//    삭제
    public void updateSkillLogStatus(Long id);
}
