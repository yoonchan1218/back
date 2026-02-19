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
    private Long jobCategorySmallId;
    private String jobCategoryName;
    private String individualMemberEducation;
    private int individualMemberPoint;
    private int individualMemberLevel;
    private int individualMemberPostCount;
    private int individualMemberQuestionCount;
    private int qnaLikeCount;
    private String memberName;
    private String filePath;    // 첨부 이미지 경로 (예: 2026/02/18)
    private String fileName;    // 첨부 이미지 파일명 (예: UUID_파일명.jpg)
    private String createdDatetime;
    private String updatedDatetime;

    public QnaVO toQnaVO() {
        return QnaVO.builder()
                .id(id)
                .individualMemberId(individualMemberId)
                .qnaTitle(qnaTitle)
                .qnaContent(qnaContent)
                .jobCategorySmallId(jobCategorySmallId)
                .jobCategoryName(jobCategoryName)
                .qnaViewCount(qnaViewCount)
                .qnaStatus(qnaStatus)
                .createdDatetime(createdDatetime)
                .updatedDatetime(updatedDatetime)
                .build();
    }

}
