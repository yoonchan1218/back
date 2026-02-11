package com.app.trycatch.mapper;

import com.app.trycatch.dto.member.CorpMemberDTO;
import com.app.trycatch.service.member.CorpService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@Transactional
public class CorpServiceTests {
//    @Autowired
//    private CorpService corpService;
//
//    @Test
//    public void testJoinCorp() {
//        CorpMemberDTO dto = new CorpMemberDTO();
//        dto.setMemberId("corp_service_test1");
//        dto.setMemberPassword("56781");
//        dto.setMemberName("박대표1");
//        dto.setMemberPhone("010-9999-11");
//        dto.setMemberEmail("corp_service1@gmail.com");
//        dto.setMemberAgreePrivacy(true);
//        dto.setMemberAgreeMarketing(false);
//
//        dto.setCorpCompanyName("캐치트라이1");
//        dto.setCorpBusinessNumber("987-65-43211");
//        dto.setCorpCeoName("박대표1");
//
//        dto.setAddressZipcode("03101");
//        dto.setAddressProvince("서울특별시");
//        dto.setAddressCity("종로구");
//        dto.setAddressDistrict("세종로");
//        dto.setAddressDetail("광화문로 456");
//
//        corpService.joinCorp(dto);
//        log.info("id: {}.......................", dto.getId());
//    }
}
