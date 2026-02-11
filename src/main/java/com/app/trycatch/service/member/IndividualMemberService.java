package com.app.trycatch.service.member;

import com.app.trycatch.common.enumeration.member.Provider;
import com.app.trycatch.domain.member.MemberVO;
import com.app.trycatch.dto.member.IndividualMemberDTO;
import com.app.trycatch.repository.member.IndividualMemberDAO;
import com.app.trycatch.repository.member.MemberDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class IndividualMemberService {
    private final MemberDAO memberDAO;
    private final IndividualMemberDAO individualMemberDAO;


    // 아이디 중복 검사 (true: 사용 가능)
    public boolean checkMemberId(String memberId) {
        return memberDAO.findByMemberId(memberId).isEmpty();
    }

    // 이메일 중복 검사 (true: 사용 가능)
    public boolean checkEmail(String memberEmail) {
        return memberDAO.findByMemberEmail(memberEmail).isEmpty();
    }


    //   개인 회원가입
    public void joinIndividual(IndividualMemberDTO individualMemberDTO) {
        MemberVO memberVO = individualMemberDTO.toMemberVO();
        memberDAO.save(memberVO);

        individualMemberDTO.setId(memberVO.getId());

        if (individualMemberDTO.getProvider() == null) {
            individualMemberDTO.setProvider(Provider.TRYCATCH);
        }

        memberDAO.saveOauth(individualMemberDTO.toOAuthVO());
        individualMemberDAO.save(individualMemberDTO.toIndividualMemberVO());
    }


}
