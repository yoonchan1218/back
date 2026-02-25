package com.app.trycatch.repository.experience;

import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.common.search.Search;
import com.app.trycatch.domain.experience.ExperienceProgramVO;
import com.app.trycatch.dto.experience.ExperienceProgramDTO;
import com.app.trycatch.mapper.experience.ExperienceProgramMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExperienceProgramDAO {
    private final ExperienceProgramMapper experienceProgramMapper;

//    프로그램 등록
    public void save(ExperienceProgramDTO experienceProgramDTO) {
        experienceProgramMapper.insert(experienceProgramDTO);
    }

//    프로그램 수정
    public void setExperienceProgram(ExperienceProgramVO experienceProgramVO) {
        experienceProgramMapper.update(experienceProgramVO);
    }

//    skill-log 최근 공고
//    목록
    public List<ExperienceProgramDTO> findAllByMemberIdOfChallenger(Criteria criteria, Search search, Long id) {
        return experienceProgramMapper.selectAllByMemberIdOfChallenger(criteria, search, id);
    }

//    개수
    public int findTotalByMemberIdOfChallenger(Search search, Long id) {
        return experienceProgramMapper.selectTotalByMemberIdOfChallenger(search, id);
    }

//    조회
    public Optional<ExperienceProgramDTO> findById(Long id) {
        return experienceProgramMapper.selectById(id);
    }

//    기업 대시보드: 상태별 프로그램 수
    public Map<String, Long> countByStatus(Long corpId) {
        return experienceProgramMapper.countByStatus(corpId);
    }

//    기업 대시보드: 최신 프로그램 N개
    public List<ExperienceProgramDTO> findLatestByCorpId(Long corpId, int limit) {
        return experienceProgramMapper.selectLatestByCorpId(corpId, limit);
    }

//    기업 프로그램 관리: 전체 개수 (필터)
    public int countByCorpId(Long corpId, String status, String keyword) {
        return experienceProgramMapper.countByCorpId(corpId, status, keyword);
    }

//    기업 프로그램 관리: 목록 (페이징 + 필터)
    public List<ExperienceProgramDTO> findByCorpId(Long corpId, Criteria criteria, String status, String keyword, String sort) {
        return experienceProgramMapper.selectByCorpId(corpId, criteria, status, keyword, sort);
    }
}
