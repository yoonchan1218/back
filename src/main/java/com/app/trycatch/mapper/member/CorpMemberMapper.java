package com.app.trycatch.mapper.member;

import com.app.trycatch.domain.member.CorpVO;
import com.app.trycatch.dto.member.CorpMemberDTO;
import com.app.trycatch.dto.member.IndividualMemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CorpMemberMapper {
    public void insert(CorpVO corpVO);
}
