package com.app.trycatch.repository.skilllog;

import com.app.trycatch.domain.skilllog.SkillLogLikeVO;
import com.app.trycatch.mapper.skilllog.SkillLogLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SkillLogLikeDAO {
    private final SkillLogLikeMapper skillLogLikeMapper;

//    좋아요 개수
    public int findCountBySkillLogId(Long id) {
        return skillLogLikeMapper.selectCountBySkillLogId(id);
    }

//    조회
    public Optional<SkillLogLikeVO> findBySkillLogIdAndMemberId(SkillLogLikeVO skillLogLikeVO) {
        return skillLogLikeMapper.selectBySkillLogIdAndMemberId(skillLogLikeVO);
    }

//    추가
    public void save(SkillLogLikeVO skillLogLikeVO) {
        skillLogLikeMapper.insert(skillLogLikeVO);
    }
//    삭제
    public void delete(Long id){
        skillLogLikeMapper.delete(id);
    }

}
