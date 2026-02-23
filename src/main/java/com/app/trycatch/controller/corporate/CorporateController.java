package com.app.trycatch.controller.corporate;

import com.app.trycatch.common.enumeration.experience.ExperienceProgramStatus;
import com.app.trycatch.dto.experience.ExperienceProgramDTO;
import com.app.trycatch.dto.member.CorpMemberDTO;
import com.app.trycatch.dto.member.IndividualMemberDTO;
import com.app.trycatch.dto.member.MemberDTO;
import com.app.trycatch.service.corporate.CorporateService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/corporate")
@RequiredArgsConstructor
@Slf4j
public class CorporateController {

    private final HttpSession session;
    private final CorporateService corporateService;

    private static final String LOGIN_REDIRECT = "redirect:/main/log-in";
    private static final String MAIN_REDIRECT = "redirect:/main/main";

    /**
     * 세션에서 회원 ID 추출 (MemberDTO / IndividualMemberDTO / CorpMemberDTO 모두 지원)
     * 로그인하지 않았으면 null 반환
     */
    private Long getMemberId() {
        Object member = session.getAttribute("member");
        if (member instanceof MemberDTO dto) return dto.getId();
        if (member instanceof IndividualMemberDTO dto) return dto.getId();
        if (member instanceof CorpMemberDTO dto) return dto.getId();
        return null;
    }

    /** 로그인 여부 확인 — 비로그인이면 true */
    private boolean notLoggedIn() {
        return getMemberId() == null;
    }

    /** 로그인했지만 기업회원이 아니면 true */
    private boolean notCorpMember() {
        Long memberId = getMemberId();
        if (memberId == null) return true;
        return !corporateService.isCorpMember(memberId);
    }

    // ── 홈 대시보드 ────────────────────────────────────────────────────

    @GetMapping("/home")
    public String home(Model model) {
        Long memberId = getMemberId();
        if (memberId != null) {
            // 로그인했는데 기업회원이 아니면 메인으로
            if (!corporateService.isCorpMember(memberId)) {
                return MAIN_REDIRECT;
            }
            model.addAttribute("corpInfo", corporateService.getCorpInfo(memberId));
            model.addAttribute("programStats", corporateService.getProgramStats(memberId));
            model.addAttribute("recentPrograms", corporateService.getRecentPrograms(memberId, 6));
        }
        model.addAttribute("recentQnas", corporateService.getRecentQnas(5));
        model.addAttribute("loginMember", session.getAttribute("member"));
        return "corporate/home";
    }

    // ── 로고 업로드 ───────────────────────────────────────────────────

    @PostMapping("/logo")
    @ResponseBody
    public Map<String, Object> uploadLogo(@RequestParam("file") MultipartFile file) throws IOException {
        if (notCorpMember()) return Map.of("success", false, "message", "기업회원만 접근할 수 있습니다.");
        Long corpId = getMemberId();
        String logoUrl = corporateService.uploadCorpLogo(corpId, file);
        return Map.of("success", true, "logoUrl", logoUrl);
    }

    @DeleteMapping("/logo")
    @ResponseBody
    public Map<String, Object> deleteLogo() {
        if (notCorpMember()) return Map.of("success", false, "message", "기업회원만 접근할 수 있습니다.");
        Long corpId = getMemberId();
        corporateService.deleteCorpLogo(corpId);
        return Map.of("success", true);
    }

    // ── 기업정보관리 ───────────────────────────────────────────────────

    @GetMapping("/profile")
    public String profileForm(Model model) {
        if (notLoggedIn()) return LOGIN_REDIRECT;
        if (notCorpMember()) return MAIN_REDIRECT;
        Long corpId = getMemberId();
        model.addAttribute("corpInfo", corporateService.getCorpInfo(corpId));
        model.addAttribute("loginMember", session.getAttribute("member"));
        return "corporate/profile";
    }

    @PostMapping("/profile")
    public String profileSave(CorpMemberDTO dto) {
        if (notCorpMember()) return MAIN_REDIRECT;
        Long corpId = getMemberId();
        dto.setId(corpId);
        // addressId는 폼에 없으므로 기존 데이터에서 조회
        CorpMemberDTO existing = corporateService.getCorpInfo(corpId);
        dto.setAddressId(existing.getAddressId());
        corporateService.updateCorpInfo(dto);
        return "redirect:/corporate/profile";
    }

    // ── 회원정보관리 ───────────────────────────────────────────────────

    @GetMapping("/member-info")
    public String memberInfoForm(Model model) {
        if (notLoggedIn()) return LOGIN_REDIRECT;
        if (notCorpMember()) return MAIN_REDIRECT;
        Long corpId = getMemberId();
        model.addAttribute("corpInfo", corporateService.getCorpInfo(corpId));
        model.addAttribute("loginMember", session.getAttribute("member"));
        return "corporate/member-info";
    }

    @PostMapping("/member-info")
    public String memberInfoSave(CorpMemberDTO dto) {
        if (notCorpMember()) return MAIN_REDIRECT;
        dto.setId(getMemberId());
        corporateService.updateMemberInfo(dto);
        return "redirect:/corporate/member-info";
    }

    // ── 팀원관리 ───────────────────────────────────────────────────────

    @GetMapping("/team-member")
    public String teamMember(@RequestParam(defaultValue = "1") int page, Model model) {
        if (notLoggedIn()) return LOGIN_REDIRECT;
        if (notCorpMember()) return MAIN_REDIRECT;
        Long corpId = getMemberId();
        model.addAttribute("teamWithPaging", corporateService.getTeamMembers(corpId, page));
        model.addAttribute("corpInfo", corporateService.getCorpInfo(corpId));
        model.addAttribute("loginMember", session.getAttribute("member"));
        return "corporate/team-member";
    }

    @PostMapping("/team-member/invite")
    public String inviteMember(@RequestParam String invitation_mail) {
        if (notCorpMember()) return MAIN_REDIRECT;
        corporateService.inviteTeamMember(getMemberId(), invitation_mail);
        return "redirect:/corporate/team-member";
    }

    @PostMapping("/team-member/remove")
    public String removeMember(@RequestParam Long memberId) {
        if (notCorpMember()) return MAIN_REDIRECT;
        corporateService.removeTeamMember(memberId, getMemberId());
        return "redirect:/corporate/team-member";
    }

    // ── 프로그램 등록 ──────────────────────────────────────────────────

    @GetMapping("/program-apply")
    public String programApplyForm(Model model) {
        if (notLoggedIn()) return LOGIN_REDIRECT;
        if (notCorpMember()) return MAIN_REDIRECT;
        Long corpId = getMemberId();
        model.addAttribute("corpInfo", corporateService.getCorpInfo(corpId));
        model.addAttribute("loginMember", session.getAttribute("member"));
        return "corporate/program-apply";
    }

    @PostMapping("/program-apply")
    public String programApplySave(ExperienceProgramDTO dto,
                                   @RequestParam(value = "programFiles", required = false) List<MultipartFile> files) {
        if (notCorpMember()) return MAIN_REDIRECT;
        Long corpId = getMemberId();
        dto.setCorpId(corpId);
        dto.setExperienceProgramStatus(ExperienceProgramStatus.RECRUITING);
        dto.setExperienceProgramDeadline(dto.getExperienceProgramStartDate());
        dto.setExperienceProgramEndDate(dto.getExperienceProgramStartDate());
        log.info("프로그램 등록 DTO: {}", dto);
        corporateService.createProgram(dto, files != null ? files : new ArrayList<>());
        return "redirect:/corporate/program-management";
    }

    // ── 프로그램관리 ───────────────────────────────────────────────────

    @GetMapping("/program-management")
    public String programManagement(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String SrchKeyword,
            @RequestParam(defaultValue = "10") int TopCount,
            Model model) {
        if (notLoggedIn()) return LOGIN_REDIRECT;
        if (notCorpMember()) return MAIN_REDIRECT;
        Long corpId = getMemberId();
        model.addAttribute("programWithPaging",
                corporateService.getPrograms(corpId, page, TopCount, status, SrchKeyword));
        model.addAttribute("programStats", corporateService.getProgramStats(corpId));
        model.addAttribute("currentStatus", status);
        model.addAttribute("currentKeyword", SrchKeyword);
        model.addAttribute("currentTopCount", TopCount);
        model.addAttribute("corpInfo", corporateService.getCorpInfo(corpId));
        model.addAttribute("loginMember", session.getAttribute("member"));
        return "corporate/program-management";
    }

    // ── 참여자관리 (tbl_challenger 구현 후 완성 예정) ───────────────────

    @GetMapping("/participant-list")
    public String participantList(
            @RequestParam Long programId,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "1") int page,
            Model model) {
        if (notLoggedIn()) return LOGIN_REDIRECT;
        if (notCorpMember()) return MAIN_REDIRECT;
        Long corpId = getMemberId();
        model.addAttribute("participantWithPaging",
                corporateService.getParticipants(programId, corpId, status, page));
        model.addAttribute("programId", programId);
        model.addAttribute("currentStatus", status);
        model.addAttribute("corpInfo", corporateService.getCorpInfo(corpId));
        model.addAttribute("loginMember", session.getAttribute("member"));
        return "corporate/participant-list";
    }

    @PostMapping("/participant/promote")
    @ResponseBody
    public Map<String, Object> promote(@RequestParam Long participantId) {
        if (notCorpMember()) return Map.of("success", false, "message", "기업회원만 접근할 수 있습니다.");
        corporateService.updateParticipantStatus(participantId, getMemberId(), "promoted");
        return Map.of("success", true);
    }

    @PostMapping("/participant/reject")
    @ResponseBody
    public Map<String, Object> reject(@RequestParam Long participantId,
                                      @RequestParam String feedback) {
        if (notCorpMember()) return Map.of("success", false, "message", "기업회원만 접근할 수 있습니다.");
        corporateService.rejectParticipant(participantId, getMemberId(), feedback);
        return Map.of("success", true);
    }

    @PostMapping("/participant/withdraw")
    @ResponseBody
    public Map<String, Object> withdraw(@RequestParam Long participantId) {
        if (notCorpMember()) return Map.of("success", false, "message", "기업회원만 접근할 수 있습니다.");
        corporateService.updateParticipantStatus(participantId, getMemberId(), "withdrawn");
        return Map.of("success", true);
    }
}
