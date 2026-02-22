package com.app.trycatch.mapper.member;

import com.app.trycatch.domain.member.CorpVO;
import com.app.trycatch.domain.member.MemberVO;
import com.app.trycatch.dto.member.CorpMemberDTO;
import com.app.trycatch.dto.member.IndividualMemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface CorpMemberMapper {
    public void insert(CorpVO corpVO);

    //    회사명 검사
    Optional<CorpVO> selectByCorpCompanyName(String corpCompanyName);

    //    사업자등록번호 검사
    Optional<CorpVO> selectByCorpBusinessNumber(String corpBusinessNumber);
}
