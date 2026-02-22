package com.app.trycatch.service.member;

import com.app.trycatch.domain.member.AddressVO;
import com.app.trycatch.domain.member.MemberVO;
import com.app.trycatch.dto.member.CorpMemberDTO;
import com.app.trycatch.repository.member.AddressDAO;
import com.app.trycatch.repository.member.CorpMemberDAO;
import com.app.trycatch.repository.member.MemberDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CorpService {
    private final MemberDAO memberDAO;
    private final CorpMemberDAO corpMemberDAO;
    private final AddressDAO addressDAO;

    // 아이디 중복 검사 (true: 사용 가능)
    public boolean checkMemberId(String memberId) {
        return memberDAO.findByMemberId(memberId).isEmpty();
    }

    // 이메일 중복 검사 (true: 사용 가능)
    public boolean checkEmail(String memberEmail) {
        return memberDAO.findByMemberEmail(memberEmail).isEmpty();
    }

    // 회사명 중복 검사 (true: 사용 가능)
    public boolean checkCompanyName(String corpCompanyName) {
        return corpMemberDAO.findByCorpCompanyName(corpCompanyName).isEmpty();
    }

    // 사업자등록번호 중복 검사 (true: 사용 가능)
    public boolean checkBusinessNumber(String corpBusinessNumber) {
        return corpMemberDAO.findByCorpBusinessNumber(corpBusinessNumber).isEmpty();
    }


    //  기업 회원가입
    public void joinCorp(CorpMemberDTO corpMemberDTO) {

        // 1. 주소 먼저 저장 → addressVO.id에 생성된 ID 세팅됨
        AddressVO addressVO = corpMemberDTO.toAddressVO();
        addressDAO.save(addressVO);

        // 2. 생성된 address ID를 DTO에 세팅
        corpMemberDTO.setAddressId(addressVO.getId());

        // 3. member 저장 (insertCorp SQL에 address_id 포함)
        MemberVO memberVO = corpMemberDTO.toMemberVO();
        memberDAO.saveCorp(memberVO);

        // 4. corp member 저장
        corpMemberDTO.setId(memberVO.getId());
        corpMemberDAO.save(corpMemberDTO.toCorpVO());
    }
}
