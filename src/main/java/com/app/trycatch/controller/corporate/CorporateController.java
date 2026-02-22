package com.app.trycatch.controller.corporate;

import com.app.trycatch.dto.member.CorpMemberDTO;
import com.app.trycatch.dto.member.MemberDTO;
import com.app.trycatch.service.corporate.CorporateService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/corporate")
@RequiredArgsConstructor
@Slf4j
public class CorporateController {

    private final HttpSession session;
    private final CorporateService corporateService;

    /** 세션에서 기업회원 id 추출 */
    private Long getCorpId() {
        Object member = session.getAttribute("member");
        if (member instanceof MemberDTO dto) return dto.getId();
        throw new IllegalStateException("기업회원 로그인이 필요합니다.");
    }

    // ── 홈 대시보드 ────────────────────────────────────────────────────

    @GetMapping("/home")
    public String home(Model model) {
        Long corpId = getCorpId();
        model.addAttribute("corpInfo", corporateService.getCorpInfo(corpId));
        model.addAttribute("programStats", corporateService.getProgramStats(corpId));
        model.addAttribute("recentPrograms", corporateService.getRecentPrograms(corpId, 5));
        model.addAttribute("loginMember", session.getAttribute("member"));
        return "corporate/home";
    }

    // ── 기업정보관리 ───────────────────────────────────────────────────

    @GetMapping("/profile")
    public String profileForm(Model model) {
        model.addAttribute("corpInfo", corporateService.getCorpInfo(getCorpId()));
        model.addAttribute("loginMember", session.getAttribute("member"));
        return "corporate/profile";
    }

    @PostMapping("/profile")
    public String profileSave(CorpMemberDTO dto) {
        Long corpId = getCorpId();
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
        model.addAttribute("corpInfo", corporateService.getCorpInfo(getCorpId()));
        model.addAttribute("loginMember", session.getAttribute("member"));
        return "corporate/member-info";
    }

    @PostMapping("/member-info")
    public String memberInfoSave(CorpMemberDTO dto) {
        dto.setId(getCorpId());
        corporateService.updateMemberInfo(dto);
        return "redirect:/corporate/member-info";
    }

    // ── 팀원관리 ───────────────────────────────────────────────────────

    @GetMapping("/team-member")
    public String teamMember(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("teamWithPaging", corporateService.getTeamMembers(getCorpId(), page));
        model.addAttribute("loginMember", session.getAttribute("member"));
        return "corporate/team-member";
    }

    @PostMapping("/team-member/invite")
    public String inviteMember(@RequestParam String invitation_mail) {
        corporateService.inviteTeamMember(getCorpId(), invitation_mail);
        return "redirect:/corporate/team-member";
    }

    @PostMapping("/team-member/remove")
    public String removeMember(@RequestParam Long memberId) {
        corporateService.removeTeamMember(memberId, getCorpId());
        return "redirect:/corporate/team-member";
    }

    // ── 프로그램관리 ───────────────────────────────────────────────────

    @GetMapping("/program-management")
    public String programManagement(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String SrchKeyword,
            @RequestParam(defaultValue = "10") int TopCount,
            Model model) {
        Long corpId = getCorpId();
        model.addAttribute("programWithPaging",
                corporateService.getPrograms(corpId, page, TopCount, status, SrchKeyword));
        model.addAttribute("programStats", corporateService.getProgramStats(corpId));
        model.addAttribute("currentStatus", status);
        model.addAttribute("currentKeyword", SrchKeyword);
        model.addAttribute("currentTopCount", TopCount);
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
        model.addAttribute("participantWithPaging",
                corporateService.getParticipants(programId, getCorpId(), status, page));
        model.addAttribute("programId", programId);
        model.addAttribute("currentStatus", status);
        model.addAttribute("loginMember", session.getAttribute("member"));
        return "corporate/participant-list";
    }

    @PostMapping("/participant/promote")
    @ResponseBody
    public Map<String, Object> promote(@RequestParam Long participantId) {
        corporateService.updateParticipantStatus(participantId, getCorpId(), "promoted");
        return Map.of("success", true);
    }

    @PostMapping("/participant/reject")
    @ResponseBody
    public Map<String, Object> reject(@RequestParam Long participantId,
                                      @RequestParam String feedback) {
        corporateService.rejectParticipant(participantId, getCorpId(), feedback);
        return Map.of("success", true);
    }

    @PostMapping("/participant/withdraw")
    @ResponseBody
    public Map<String, Object> withdraw(@RequestParam Long participantId) {
        corporateService.updateParticipantStatus(participantId, getCorpId(), "withdrawn");
        return Map.of("success", true);
    }
}
