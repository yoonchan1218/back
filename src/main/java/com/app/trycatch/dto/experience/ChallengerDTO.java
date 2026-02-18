package com.app.trycatch.dto.experience;

import com.app.trycatch.common.enumeration.experience.ApplyStatus;
import com.app.trycatch.common.enumeration.experience.ChallengerStatus;
import com.app.trycatch.domain.experience.ChallengerVO;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ChallengerDTO {
    private Long id;
    private Long applyId;
    private ChallengerStatus challengerStatus;
    private String createdDatetime;
    private String updatedDatetime;

    public ChallengerVO toVO() {
        return ChallengerVO.builder()
                .id(id)
                .applyId(applyId)
                .challengerStatus(challengerStatus)
                .createdDatetime(createdDatetime)
                .updatedDatetime(updatedDatetime)
                .build();
    }
}
