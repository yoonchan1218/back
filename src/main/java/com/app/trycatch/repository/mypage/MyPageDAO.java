package com.app.trycatch.repository.mypage;

import com.app.trycatch.dto.mypage.MyPageNotificationDTO;
import com.app.trycatch.dto.mypage.MyPageProfileDTO;
import com.app.trycatch.dto.mypage.MyPageUpdateDTO;
import com.app.trycatch.mapper.mypage.MyPageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyPageDAO {
    private final MyPageMapper myPageMapper;

    public Optional<MyPageProfileDTO> findProfileByMemberId(Long memberId) {
        return myPageMapper.selectProfileByMemberId(memberId);
    }

    public void updateMember(MyPageUpdateDTO myPageUpdateDTO) {
        myPageMapper.updateMember(myPageUpdateDTO);
    }

    public void updateIndividualMember(MyPageUpdateDTO myPageUpdateDTO) {
        myPageMapper.updateIndividualMember(myPageUpdateDTO);
    }

    public List<MyPageNotificationDTO> findNotificationsByMemberId(Long memberId) {
        return myPageMapper.selectNotificationsByMemberId(memberId);
    }

    public void readNotification(Long memberId, Long notificationId) {
        myPageMapper.updateNotificationRead(memberId, notificationId);
    }

    public void deactivateMember(Long memberId) {
        myPageMapper.updateMemberStatusToInactive(memberId);
    }
}
