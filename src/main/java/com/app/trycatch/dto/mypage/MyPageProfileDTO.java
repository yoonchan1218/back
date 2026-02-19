package com.app.trycatch.dto.mypage;

import com.app.trycatch.common.enumeration.member.Gender;
import com.app.trycatch.common.enumeration.member.Status;
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
public class MyPageProfileDTO {
    private Long id;
    private String memberId;
    private String memberName;
    private String memberEmail;
    private String memberPhone;
    private Long addressId;
    private String addressZipcode;
    private String addressText;
    private String addressDetail;
    private Status memberStatus;

    private String individualMemberBirth;
    private Gender individualMemberGender;
    private String individualMemberEducation;
    private int individualMemberPoint;
    private int individualMemberLevel;
    private int individualMemberPostCount;
    private int individualMemberQuestionCount;

    private String createdDatetime;
    private String updatedDatetime;
}
