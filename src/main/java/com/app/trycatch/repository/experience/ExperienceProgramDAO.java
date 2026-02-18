package com.app.trycatch.repository.experience;

import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.common.search.Search;
import com.app.trycatch.dto.experience.ExperienceProgramDTO;
import com.app.trycatch.mapper.experience.ExperienceProgramMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.awt.desktop.OpenFilesEvent;
import java.util.List;
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
}
