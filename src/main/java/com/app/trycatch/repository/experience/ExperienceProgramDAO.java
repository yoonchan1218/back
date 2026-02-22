package com.app.trycatch.repository.experience;

import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.common.search.Search;
import com.app.trycatch.dto.experience.ExperienceProgramDTO;
import com.app.trycatch.mapper.experience.ExperienceProgramMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExperienceProgramDAO {
    private final ExperienceProgramMapper experienceProgramMapper;

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

//    기업 - 프로그램 목록
    public List<ExperienceProgramDTO> findByCorpId(Long corpId, Criteria criteria, String status, String keyword) {
        return experienceProgramMapper.selectByCorpId(corpId, criteria, status, keyword);
    }

//    기업 - 프로그램 수
    public int countByCorpId(Long corpId, String status, String keyword) {
        return experienceProgramMapper.selectCountByCorpId(corpId, status, keyword);
    }

//    기업 - 상태별 프로그램 수
    public Map<String, Long> countByStatus(Long corpId) {
        List<Map<String, Object>> rows = experienceProgramMapper.selectCountByStatus(corpId);
        Map<String, Long> result = new HashMap<>();
        for (String s : List.of("draft", "recruiting", "closed", "cancelled")) {
            result.put(s, 0L);
        }
        for (Map<String, Object> row : rows) {
            String status = (String) row.get("status");
            Object cnt = row.get("cnt");
            long count = cnt instanceof Long ? (Long) cnt : ((Number) cnt).longValue();
            result.put(status, count);
        }
        return result;
    }

//    기업 - 최신 프로그램 N개
    public List<ExperienceProgramDTO> findLatestByCorpId(Long corpId, int limit) {
        return experienceProgramMapper.selectLatestByCorpId(corpId, limit);
    }
}
