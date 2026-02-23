package com.app.trycatch.mapper;

import com.app.trycatch.domain.member.AddressVO;
import com.app.trycatch.domain.member.MemberVO;
import com.app.trycatch.dto.member.CorpMemberDTO;
import com.app.trycatch.mapper.member.AddressMapper;
import com.app.trycatch.mapper.member.CorpMemberMapper;
import com.app.trycatch.mapper.member.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class CorpMemberTests {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private CorpMemberMapper corpMemberMapper;

    @Test
    public void testInsert() {
//        CorpMemberDTO corpMemberDTO = new CorpMemberDTO();
//        corpMemberDTO.setMemberId("corp_test");
//        corpMemberDTO.setMemberPassword("1234");
//        corpMemberDTO.setMemberName("김대표");
//        corpMemberDTO.setMemberPhone("010-5678-5678");
//        corpMemberDTO.setMemberEmail("corp@gmail.com");
//        corpMemberDTO.setMemberAgreePrivacy(true);
//        corpMemberDTO.setMemberAgreeMarketing(false);
//
//        corpMemberDTO.setAddressZipcode("06100");
//        corpMemberDTO.setAddressProvince("서울특별시");
//        corpMemberDTO.setAddressCity("강남구");
//        corpMemberDTO.setAddressDistrict("역삼동");
//        corpMemberDTO.setAddressDetail("테헤란로 123");
//
//        corpMemberDTO.setCorpCompanyName("트라이캐치");
//        corpMemberDTO.setCorpBusinessNumber("123-45-67890");
//        corpMemberDTO.setCorpCeoName("김대표");
//        log.info("{}...........", corpMemberDTO);
//
//        AddressVO addressVO = corpMemberDTO.toAddressVO();
//        addressMapper.insert(addressVO);
//        log.info("{}................",addressVO);
//
//        corpMemberDTO.setAddressId(addressVO.getId());
//        MemberVO memberVO = corpMemberDTO.toMemberVO();
//        memberMapper.insertCorp(memberVO);
//        log.info("{}................",memberVO.getId());
//
//        corpMemberDTO.setId(memberVO.getId());
//
//        corpMemberMapper.insert(corpMemberDTO.toCorpVO());
    }
}
