package com.app.trycatch.dto.member;

import com.app.trycatch.common.enumeration.member.Gender;
import com.app.trycatch.common.enumeration.member.Status;
import com.app.trycatch.domain.member.AddressVO;
import com.app.trycatch.domain.member.CorpVO;
import com.app.trycatch.domain.member.MemberVO;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class CorpMemberDTO {
    private Long id;
    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberEmail;
    private String memberPhone;
    private Long addressId;
    private Status memberStatus;
    private boolean memberAgreePrivacy;
    private boolean memberAgreeMarketing;
    private String corpCompanyName;
    private String corpBusinessNumber;
    private String corpCeoName;
    private Long corpKindId;
    private Long corpKindSmallId;
    private String corpCompanyType;
    private String corpCompanySize;
    private String corpEstablishmentDate;
    private String corpWebsiteUrl;
    private String corpFax;
    private Long corpCapital;
    private Long corpTotalSales;
    private String corpPerformance;
    private String corpVision;
    private String addressZipcode;
    private String addressAddress;
    private String addressDetail;
    private String createdDatetime;
    private String updatedDatetime;

    public MemberVO toMemberVO() {
        return MemberVO.builder()
                .id(id)
                .memberId(memberId)
                .memberPassword(memberPassword)
                .memberName(memberName)
                .memberEmail(memberEmail)
                .memberPhone(memberPhone)
                .addressId(addressId)
                .memberStatus(memberStatus)
                .memberAgreePrivacy(memberAgreePrivacy)
                .memberAgreeMarketing(memberAgreeMarketing)
                .createdDatetime(createdDatetime)
                .updatedDatetime(updatedDatetime)
                .build();
    }

    public CorpVO toCorpVO() {
        return CorpVO.builder()
                .id(id)
                .corpCompanyName(corpCompanyName)
                .corpBusinessNumber(corpBusinessNumber)
                .corpCeoName(corpCeoName)
                .corpKindId(corpKindId)
                .corpKindSmallId(corpKindSmallId)
                .corpCompanyType(corpCompanyType)
                .corpCompanySize(corpCompanySize)
                .corpEstablishmentDate(corpEstablishmentDate)
                .corpWebsiteUrl(corpWebsiteUrl)
                .corpFax(corpFax)
                .corpCapital(corpCapital)
                .corpTotalSales(corpTotalSales)
                .corpPerformance(corpPerformance)
                .corpVision(corpVision)
                .build();
    }

    public AddressVO toAddressVO() {
        return AddressVO.builder()
                .id(addressId)
                .addressZipcode(addressZipcode)
                .addressAddress(addressAddress)
                .addressDetail(addressDetail)
                .build();
    }

}
