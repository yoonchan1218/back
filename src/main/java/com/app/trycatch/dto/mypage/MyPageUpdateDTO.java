package com.app.trycatch.dto.mypage;

import com.app.trycatch.common.enumeration.member.Gender;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class MyPageUpdateDTO {
    private Long id;
    private String memberName;
    private String memberEmail;
    private String memberPhone;
    private String individualMemberBirth;
    private Gender individualMemberGender;
    private String individualMemberEducation;
}
