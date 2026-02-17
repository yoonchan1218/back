package com.app.trycatch.mapper.mypage;

import com.app.trycatch.dto.mypage.MyPageNotificationDTO;
import com.app.trycatch.dto.mypage.MyPageProfileDTO;
import com.app.trycatch.dto.mypage.MyPageUpdateDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MyPageMapper {
    Optional<MyPageProfileDTO> selectProfileByMemberId(Long memberId);

    void updateMember(MyPageUpdateDTO myPageUpdateDTO);

    void updateIndividualMember(MyPageUpdateDTO myPageUpdateDTO);

    List<MyPageNotificationDTO> selectNotificationsByMemberId(Long memberId);

    void updateNotificationRead(@Param("memberId") Long memberId, @Param("notificationId") Long notificationId);

    void updateMemberStatusToInactive(Long memberId);
}
