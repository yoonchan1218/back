package com.app.trycatch.repository.member;

import com.app.trycatch.domain.member.CorpVO;
import com.app.trycatch.domain.member.MemberVO;
import com.app.trycatch.mapper.member.CorpMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CorpMemberDAO {
    private final CorpMemberMapper corpMemberMapper;

    public void save(CorpVO corpVO) {
        corpMemberMapper.insert(corpVO);
    }

    //    기업명 검사
    public Optional<CorpVO> findByCorpCompanyName(String corpCompanyName) {
        return corpMemberMapper.selectByCorpCompanyName(corpCompanyName);
    }

    //    사업자등록번호 검사
    public Optional<CorpVO> findByCorpBusinessNumber(String corpBusinessNumber) {
        return corpMemberMapper.selectByCorpBusinessNumber(corpBusinessNumber);
    }

}