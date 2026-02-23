package com.app.trycatch.repository.mypage;

import com.app.trycatch.dto.mypage.ApplyListDTO;
import com.app.trycatch.mapper.mypage.ApplyListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ApplyListDAO {
    private final ApplyListMapper applyListMapper;

    public List<ApplyListDTO> findAllByMemberId(Long memberId) {
        return applyListMapper.selectAllByMemberId(memberId);
    }

    public List<ApplyListDTO> findAllByMemberIdWithFilter(
            Long memberId, String fromDt, String toDt,
            String programStatus, String applyStatus, String keyword) {
        return applyListMapper.selectAllByMemberIdWithFilter(
                memberId, fromDt, toDt, programStatus, applyStatus, keyword);
    }
}
