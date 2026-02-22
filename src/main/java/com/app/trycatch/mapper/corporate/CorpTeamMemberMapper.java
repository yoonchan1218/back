package com.app.trycatch.mapper.corporate;

import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.domain.corporate.CorpTeamMemberVO;
import com.app.trycatch.dto.corporate.CorpTeamMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CorpTeamMemberMapper {

    // 팀원 추가 (초대)
    void insert(CorpTeamMemberVO vo);

    // 팀원 목록 조회 (페이징)
    List<CorpTeamMemberDTO> selectByCorpId(
            @Param("corpId") Long corpId,
            @Param("criteria") Criteria criteria);

    // 팀원 수 조회
    int selectCountByCorpId(@Param("corpId") Long corpId);

    // 팀원 제거
    void delete(@Param("id") Long id, @Param("corpId") Long corpId);
}
