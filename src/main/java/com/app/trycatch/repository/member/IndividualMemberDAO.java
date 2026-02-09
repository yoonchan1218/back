package com.app.trycatch.repository.member;

import com.app.trycatch.domain.member.IndividualMemberVO;
import com.app.trycatch.mapper.member.IndividualMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IndividualMemberDAO {
    private final IndividualMemberMapper individualMemberMapper;

    public void save(IndividualMemberVO individualMemberVO) {
        individualMemberMapper.insert(individualMemberVO);
    }
}