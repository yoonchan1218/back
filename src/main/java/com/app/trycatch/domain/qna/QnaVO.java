package com.app.trycatch.domain.qna;

import com.app.trycatch.audit.Period;
import com.app.trycatch.common.enumeration.qna.QnaStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @ToString(callSuper=true)
@EqualsAndHashCode(of="id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class QnaVO extends Period {
    private Long id;
    private Long memberId;
    private String qnaTitle;
    private String qnaContent;
    private int qnaViewCount;
    private QnaStatus qnaStatus;
}
