package com.app.trycatch.dto.qna;

import com.app.trycatch.domain.qna.QnaCommentVO;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class QnaCommentDTO {
    private Long id;
    private Long qnaId;
    private Long individualMemberId;
    private String memberName;
    private Long qnaCommentParent;
    private String qnaCommentContent;
    private String createdDatetime;
    private String updatedDatetime;

    public QnaCommentVO toQnaCommentVO() {
        return QnaCommentVO.builder()
                .id(id)
                .qnaId(qnaId)
                .individualMemberId(individualMemberId)
                .qnaCommentParent(qnaCommentParent)
                .qnaCommentContent(qnaCommentContent)
                .build();
    }
}

