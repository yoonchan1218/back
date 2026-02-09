package com.app.trycatch.dto.qna;

import com.app.trycatch.common.enumeration.qna.QnaStatus;
import com.app.trycatch.domain.qna.QnaVO;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
@NoArgsConstructor
public class QnaDTO {
    private Long id;
    private Long individualMemberId;
    private String qnaTitle;
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

    public QnaVO toQnaVO() {
        return QnaVO.builder()
                .id(id)
                .individualMemberId(individualMemberId)
                .qnaTitle(qnaTitle)
                .qnaContent(qnaContent)
                .qnaViewCount(qnaViewCount)
                .qnaStatus(qnaStatus)
                .createdDatetime(createdDatetime)
                .updatedDatetime(updatedDatetime)
                .build();
    }



}
