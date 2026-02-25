package com.app.trycatch.repository.point;

import com.app.trycatch.domain.point.PointDetailsVO;
import com.app.trycatch.dto.point.PointDetailsDTO;
import com.app.trycatch.mapper.point.PointDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PointDetailsDAO {
    private final PointDetailsMapper pointDetailsMapper;

    public List<PointDetailsDTO> findAllByMemberId(Long memberId) {
        return pointDetailsMapper.selectAllByMemberId(memberId);
    }

    public void save(PointDetailsVO pointDetailsVO) {
        pointDetailsMapper.insert(pointDetailsVO);
    }
}
