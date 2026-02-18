package com.app.trycatch.dto.skilllog;

import com.app.trycatch.common.enumeration.skillLog.SkillLogStatus;
import com.app.trycatch.domain.member.MemberVO;
import com.app.trycatch.domain.skilllog.SkillLogVO;
import com.app.trycatch.dto.experience.ExperienceProgramDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class SkillLogDTO {
//    skillLog
    private Long id;
    private String skillLogTitle;
    private String skillLogContent;
    private int skillLogViewCount;
    private SkillLogStatus skillLogStatus;
    private String createdDatetime;
    private String updatedDatetime;

//    member
    private Long memberId;
    private String memberName;

//    like
    private int likeCount;
    private boolean liked;

//    experienceProgram
    private Long experienceProgramId;
    private ExperienceProgramDTO experienceProgram;
//    참여한 체험 공고 제목, 해당 기업 이름, 로고 이미지 필요

//    tag 목록
    private List<TagDTO> tags = new ArrayList<>();

//    file 목록
    private List<SkillLogFileDTO> skillLogFiles = new ArrayList<>();

    public SkillLogVO toSkillLogVO() {
        return SkillLogVO.builder()
                .id(id)
                .memberId(memberId)
                .experienceProgramId(experienceProgramId)
                .skillLogTitle(skillLogTitle)
                .skillLogContent(skillLogContent)
                .skillLogViewCount(skillLogViewCount)
                .skillLogStatus(skillLogStatus)
                .createdDatetime(createdDatetime)
                .updatedDatetime(updatedDatetime)
                .build();
    }

    public MemberVO toMemberVO() {
        return MemberVO.builder()
                .id(memberId)
                .memberName(memberName)
                .build();
    }
}
