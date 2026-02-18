package com.app.trycatch.dto.skilllog;

import com.app.trycatch.domain.skilllog.SkillLogLikeVO;
import com.app.trycatch.domain.skilllog.TagVO;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class SkillLogLikeDTO {
    private Long id;
    private Long memberId;
    private Long skillLogId;
    private String createdDatetime;
    private String updatedDatetime;

    public SkillLogLikeVO toVO() {
        return SkillLogLikeVO.builder()
                .id(id)
                .memberId(memberId)
                .skillLogId(skillLogId)
                .createdDatetime(createdDatetime)
                .updatedDatetime(updatedDatetime)
                .build();
    }
}
