package com.app.trycatch.controller.mypage;

import com.app.trycatch.dto.member.IndividualMemberDTO;
import com.app.trycatch.dto.member.MemberDTO;
import com.app.trycatch.dto.mypage.MyPageUpdateDTO;
import com.app.trycatch.service.mypage.MyPageService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;
    private final HttpSession session;

    @GetMapping({"", "/"})
    public RedirectView goToMyPageRoot() {
        return new RedirectView("/mypage/mypage");
    }

    @GetMapping("mypage")
    public String goToMyPage(Model model) {
        Long memberId = getSessionMemberId();
        model.addAttribute("profile", myPageService.getProfile(memberId));
        return "mypage/mypage";
    }

    @GetMapping("change-my-information")
    public String goToChangeMyInformation(Model model) {
        Long memberId = getSessionMemberId();
        model.addAttribute("profile", myPageService.getProfile(memberId));
        return "mypage/change-my-information";
    }

    @PostMapping("change-my-information")
    public RedirectView changeMyInformation(MyPageUpdateDTO myPageUpdateDTO) {
        Long memberId = getSessionMemberId();
        myPageService.updateProfile(memberId, myPageUpdateDTO);
        return new RedirectView("/mypage/mypage");
    }

    @GetMapping("notification")
    public String goToNotification(Model model) {
        Long memberId = getSessionMemberId();
        model.addAttribute("notifications", myPageService.getNotifications(memberId));
        return "mypage/notification";
    }

    @PostMapping("notification/read")
    @ResponseBody
    public boolean readNotification(Long notificationId) {
        Long memberId = getSessionMemberId();
        myPageService.readNotification(memberId, notificationId);
        return true;
    }

    @GetMapping("experience")
    public String goToExperience(Model model) {
        Long memberId = getSessionMemberId();
        model.addAttribute("profile", myPageService.getProfile(memberId));
        return "mypage/experience";
    }

    @GetMapping("unsubscribe")
    public String goToUnsubscribe(Model model) {
        Long memberId = getSessionMemberId();
        model.addAttribute("profile", myPageService.getProfile(memberId));
        return "mypage/unsubscribe";
    }

    @PostMapping("unsubscribe")
    public RedirectView unsubscribe(String memberName) {
        Long memberId = getSessionMemberId();
        myPageService.deactivateMember(memberId, memberName);
        session.invalidate();
        return new RedirectView("/main/log-in");
    }

    @PostMapping("profile-image")
    @ResponseBody
    public String uploadProfileImage(@RequestParam("file") MultipartFile file) {
        return myPageService.uploadProfileImage(file);
    }

    @GetMapping("logout")
    public RedirectView logout() {
        Object member = session.getAttribute("member");
        if (member instanceof MemberDTO memberDTO && memberDTO.getProvider() == com.app.trycatch.common.enumeration.member.Provider.KAKAO) {
            session.invalidate();
            return new RedirectView("https://kauth.kakao.com/oauth/logout?client_id=6c9664c00ac5573fa3d8f1caf80e67f3&logout_redirect_uri=http://localhost:10000/main/log-in");
        }
        session.invalidate();
        return new RedirectView("/main/log-in");
    }

    private Long getSessionMemberId() {
        Object member = session.getAttribute("member");
        if (member instanceof MemberDTO memberDTO && memberDTO.getId() != null) {
            return memberDTO.getId();
        }
        if (member instanceof IndividualMemberDTO individualMemberDTO && individualMemberDTO.getId() != null) {
            return individualMemberDTO.getId();
        }
        throw new com.app.trycatch.common.exception.UnauthorizedMemberAccessException();
    }
}
