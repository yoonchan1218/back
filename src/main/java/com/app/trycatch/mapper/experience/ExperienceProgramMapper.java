package com.app.trycatch.mapper.experience;

import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.common.search.Search;
import com.app.trycatch.dto.experience.ExperienceProgramDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ExperienceProgramMapper {
//    skill-log 최근 공고
//    목록
    public List<ExperienceProgramDTO> selectAllByMemberIdOfChallenger(@Param("criteria") Criteria criteria,
                                                             @Param("search") Search search, @Param("id") Long id);
//    개수
    public int selectTotalByMemberIdOfChallenger(@Param("search") Search search, @Param("id") Long id);

//    조회
    public Optional<ExperienceProgramDTO> selectById(Long id);
}
