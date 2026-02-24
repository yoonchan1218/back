package com.app.trycatch.repository.experience;

import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.dto.experience.ChallengerDTO;
import com.app.trycatch.mapper.experience.ChallengerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ChallengerDAO {
    private final ChallengerMapper challengerMapper;

//    참여자 목록
    public List<ChallengerDTO> findByProgramId(Long programId, String status, Criteria criteria) {
        return challengerMapper.selectByProgramId(programId, status, criteria);
    }

//    참여자 수
    public int countByProgramId(Long programId, String status) {
        return challengerMapper.selectCountByProgramId(programId, status);
    }

//    상태별 참여자 수
    public Map<String, Long> countStatusByProgramId(Long programId) {
        return challengerMapper.selectStatusCountByProgramId(programId);
    }

//    상태 변경
    public void setStatus(Long id, String challengerStatus) {
        challengerMapper.updateStatus(id, challengerStatus);
    }
}
