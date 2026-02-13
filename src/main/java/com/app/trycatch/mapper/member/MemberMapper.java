package com.app.trycatch.mapper.member;

import com.app.trycatch.domain.member.MemberVO;
import com.app.trycatch.domain.member.OAuthVO;
import com.app.trycatch.dto.member.CorpMemberDTO;
import com.app.trycatch.dto.member.IndividualMemberDTO;
import com.app.trycatch.dto.member.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    //    아이디 검사
    Optional<MemberVO> selectByMemberId(String memberId);

    //    이메일 검사
    Optional<MemberVO> selectByMemberEmail(String memberEmail);

    //    oauth
    public void insertOauth(OAuthVO oAuthVO);

    //    개인 회원 가입
    public void insert(MemberVO memberVO);

    //      기업 회원 가입
    public void insertCorp(MemberVO memberVO);

    //      주소 id 추가
    public void updateAddressIdById(Long id);

    //    로그인
    public Optional<MemberVO> selectMemberForLogin(MemberDTO memberDTO);
}
