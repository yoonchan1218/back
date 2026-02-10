package com.app.trycatch.dto.qna;

import com.app.trycatch.common.enumeration.qna.QnaStatus;
import com.app.trycatch.domain.member.IndividualMemberVO;
import com.app.trycatch.domain.qna.QnaCommentVO;
import com.app.trycatch.domain.qna.QnaVO;
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
    private Long individualMemberId;
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

    public QnaCommentVO toQnaCommentVO() {
        return QnaCommentVO.builder()
                .id(id)
                .qnaId(qnaId)
                .memberId(memberId)
                .qnaCommentParent(qnaCommentParent)
                .commentContent(commentContent)
                .qnaCommentLikeCount(qnaCommentLikeCount)
                .createdDatetime(createdDateTime)
                .updatedDatetime(updatedDateTime)
                .build();
    }

    public IndividualMemberVO toIndividualMemberVO() {
        return IndividualMemberVO.builder()
                .id(id)
                .individualMemberBirth(individualMemberBirth)
                .individualMemberEducation(individualMemberEducation)
                .individualMemberPoint(individualMemberPoint)
                .individualMemberLevel(individualMemberLevel)
                .individualMemberPostCount(individualMemberPostCount)
                .individualMemberQuestionCount(individualMemberQuestionCount)
                .build();
    }

    public QnaVO toQnaVO() {
        return QnaVO.builder()
                .id(id)
                .individualMemberId(individualMemberId)
                .qnaTitle(qnaTitle)
                .qnaContent(qnaContent)
                .qnaViewCount(qnaViewCount)
                .qnaStatus(qnaStatus)
                .build();
    }

}

