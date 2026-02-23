package com.app.trycatch.mapper.mypage;

import com.app.trycatch.dto.mypage.ApplyListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplyListMapper {
    List<ApplyListDTO> selectAllByMemberId(@Param("memberId") Long memberId);

    List<ApplyListDTO> selectAllByMemberIdWithFilter(
            @Param("memberId") Long memberId,
            @Param("fromDt") String fromDt,
            @Param("toDt") String toDt,
            @Param("programStatus") String programStatus,
            @Param("applyStatus") String applyStatus,
            @Param("keyword") String keyword);
}
