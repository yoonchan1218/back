package com.app.trycatch.mapper.experience;

import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.common.search.Search;
import com.app.trycatch.dto.experience.ExperienceProgramDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
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

//    기업 대시보드: 상태별 프로그램 수
    Map<String, Long> countByStatus(@Param("corpId") Long corpId);

//    기업 대시보드: 최신 프로그램 N개
    List<ExperienceProgramDTO> selectLatestByCorpId(@Param("corpId") Long corpId, @Param("limit") int limit);

//    기업 프로그램 관리: 전체 개수 (필터)
    int countByCorpId(@Param("corpId") Long corpId, @Param("status") String status, @Param("keyword") String keyword);

//    기업 프로그램 관리: 목록 (페이징 + 필터)
    List<ExperienceProgramDTO> selectByCorpId(@Param("corpId") Long corpId, @Param("criteria") Criteria criteria,
                                              @Param("status") String status, @Param("keyword") String keyword);
}
