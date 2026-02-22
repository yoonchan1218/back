package com.app.trycatch.dto.mypage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ExperienceProgramRankDTO {
    private Long experienceProgramId;
    private String experienceProgramTitle;
    private String experienceProgramJob;
    private String experienceProgramDeadline;
    private int experienceProgramViewCount;
    private Long corpId;
    private String corpCompanyName;
}
