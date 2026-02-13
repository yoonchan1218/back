package com.app.trycatch.dto.experience;

import com.app.trycatch.common.enumeration.experience.ExperienceProgramStatus;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ExperienceProgramDTO {
    private Long id;
    private Long corpId;
    private Long addressId;
    private String experienceProgramTitle;
    private String experienceProgramDescription;
    private String experienceProgramLevel;
    private String experienceProgramRecruitmentCount;
    private String experienceProgramWorkDays;
    private String experienceProgramWorkHours;
    private String experienceProgramStartDate;
    private String experienceProgramEndDate;
    private String experienceProgramDeadline;
    private ExperienceProgramStatus experienceProgramStatus;
    private int experienceProgramViewCount;
    private String experienceProgramJob;
    private String createdDatetime;
    private String updatedDatetime;


}
