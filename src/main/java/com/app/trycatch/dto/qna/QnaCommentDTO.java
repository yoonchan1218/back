package com.app.trycatch.dto.qna;

import com.app.trycatch.common.enumeration.qna.QnaStatus;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
@NoArgsConstructor
public class QnaCommentDTO {
    private Long id;
    private Long qnaId;
    private String qnaTitle;
    private String qnaContent;
    private int qnaViewCount;
    private QnaStatus qnaStatus;
    private Long memberId;
    private String individualMemberBirth;
    private String individualMemberEducation;
    private int individualMemberPoint;
    private int individualMemberLevel;
    private int individualMemberPostCount;
    private int individualMemberQuestionCount;
    private Long qnaCommentParent;
    private String commentContent;
    private int qnaCommentLikeCount;
    private String createdDateTime;
    private String updatedDateTime;

}
