package com.app.trycatch.service.mypage;

import com.app.trycatch.common.exception.InputAllDataException;
import com.app.trycatch.common.exception.MemberNotFoundException;
import com.app.trycatch.common.exception.UnauthorizedMemberAccessException;
import com.app.trycatch.dto.mypage.MyPageNotificationDTO;
import com.app.trycatch.dto.mypage.MyPageProfileDTO;
import com.app.trycatch.dto.mypage.MyPageUpdateDTO;
import com.app.trycatch.repository.mypage.MyPageDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MyPageService {
    private final MyPageDAO myPageDAO;

    @Transactional(readOnly = true)
    public MyPageProfileDTO getProfile(Long memberId) {
        return myPageDAO.findProfileByMemberId(memberId).orElseThrow(MemberNotFoundException::new);
    }

    public void updateProfile(Long memberId, MyPageUpdateDTO myPageUpdateDTO) {
        if (myPageUpdateDTO.getId() != null && !memberId.equals(myPageUpdateDTO.getId())) {
            throw new UnauthorizedMemberAccessException();
        }

        // 필수 정보 검증 (이름, 이메일, 휴대폰, 생년월일, 성별)
        if (myPageUpdateDTO.getMemberName() == null || myPageUpdateDTO.getMemberName().isBlank() ||
            myPageUpdateDTO.getMemberEmail() == null || myPageUpdateDTO.getMemberEmail().isBlank() ||
            myPageUpdateDTO.getMemberPhone() == null || myPageUpdateDTO.getMemberPhone().isBlank() ||
            myPageUpdateDTO.getIndividualMemberBirth() == null || myPageUpdateDTO.getIndividualMemberBirth().isBlank() ||
            myPageUpdateDTO.getIndividualMemberGender() == null) {
            throw new InputAllDataException();
        }

        myPageUpdateDTO.setId(memberId);
        myPageDAO.updateMember(myPageUpdateDTO.toMemberVO());
        myPageDAO.updateIndividualMember(myPageUpdateDTO.toIndividualMemberVO());
    }

    @Transactional(readOnly = true)
    public List<MyPageNotificationDTO> getNotifications(Long memberId) {
        return myPageDAO.findNotificationsByMemberId(memberId);
    }

    public void readNotification(Long memberId, Long notificationId) {
        if (notificationId == null) {
            return;
        }
        myPageDAO.readNotification(memberId, notificationId);
    }

    public String uploadProfileImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        String todayPath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String rootPath = "C:/file/";
        String path = rootPath + todayPath;

        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "_" + file.getOriginalFilename();

        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            file.transferTo(new File(path, fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "/api/files/display?filePath=" + todayPath + "&fileName=" + fileName;
    }

    public void deactivateMember(Long memberId, String memberName) {
        MyPageProfileDTO profile = myPageDAO.findProfileByMemberId(memberId)
                .orElseThrow(MemberNotFoundException::new);
        if (!profile.getMemberName().equals(memberName)) {
            throw new com.app.trycatch.common.exception.UnsubscribeNameMismatchException();
        }
        myPageDAO.deactivateMember(memberId);
    }
}
