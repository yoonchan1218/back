package com.app.trycatch.dto.skilllog;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class SkillLogDashboardDTO {
    private Long id;
    private String memberName;
    private int individualMemberLevel;
    private int individualMemberPoint;
    private int skillLogViewCountTotal;
    private int skillLogLikeCountTotal;
    private int skillLogCount;
    private int skillLogCommentCount;

//    프로필 파일

}
