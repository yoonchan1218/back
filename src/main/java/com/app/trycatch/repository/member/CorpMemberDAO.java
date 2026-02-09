package com.app.trycatch.repository.member;

import com.app.trycatch.domain.member.CorpVO;
import com.app.trycatch.mapper.member.CorpMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CorpMemberDAO {
    private final CorpMemberMapper corpMemberMapper;

    public void save(CorpVO corpVO) {
        corpMemberMapper.insert(corpVO);
    }
}