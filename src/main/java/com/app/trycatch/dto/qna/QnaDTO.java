package com.app.trycatch.dto.qna;

import com.app.trycatch.common.enumeration.qna.QnaStatus;
import com.app.trycatch.domain.qna.QnAVO;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
@NoArgsConstructor
public class QnaDTO {
    private Long id;
    private Long memberId;
    private String qnatitle;
    private String qnaContent;
    private int qnaViewCount;
    private QnaStatus qnaStatus;
    private String individualMemberEducation;
    private int individualMemberPoint;
    private int individualMemberLevel;
    private int individualMemberPostCount;
    private int individualMemberQuestionCount;
    private String createdDatetime;
    private String updatedDatetime;

    public QnAVO toQnaVO() {
        return QnAVO.builder()
                .id(id)
                .memberId(memberId)
                .qnatitle(qnatitle)
                .qnaContent(qnaContent)
                .qnaViewCount(qnaViewCount)
                .qnaStatus(qnaStatus)
                .createdDatetime(createdDatetime)
                .updatedDatetime(updatedDatetime)
                .build();
    }

    public


}
