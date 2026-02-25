package com.app.trycatch.dto.point;

import com.app.trycatch.common.enumeration.point.PointType;
import com.app.trycatch.domain.point.PointDetailsVO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class PointDetailsDTO {
    private Long id;
    private Long memberId;
    private PointType pointType;
    private int pointAmount;
    private String createdDatetime;
    private String updatedDatetime;

    public PointDetailsVO toVO() {
        return PointDetailsVO.builder()
                .id(id)
                .memberId(memberId)
                .pointType(pointType)
                .pointAmount(pointAmount)
                .createdDatetime(createdDatetime)
                .updatedDatetime(updatedDatetime)
                .build();
    }
}
