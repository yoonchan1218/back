package com.app.trycatch.mapper.mypage;

import com.app.trycatch.dto.mypage.ApplyListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplyListMapper {
    List<ApplyListDTO> selectAllByMemberId(@Param("memberId") Long memberId);
}
