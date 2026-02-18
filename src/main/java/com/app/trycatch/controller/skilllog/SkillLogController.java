package com.app.trycatch.controller.skilllog;

import com.app.trycatch.dto.member.IndividualMemberDTO;
import com.app.trycatch.dto.member.MemberDTO;
import com.app.trycatch.dto.skilllog.SkillLogDTO;
import com.app.trycatch.service.skilllog.SkillLogService;
import com.app.trycatch.service.tag.TagService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@Controller
@RequestMapping("/skill-log/**")
@RequiredArgsConstructor
@Slf4j
public class SkillLogController {
    private final SkillLogService skillLogService;
    private final TagService tagService;
    private final HttpSession session;

    @GetMapping("write")
    public String goToWrite(Model model){
        Object member = session.getAttribute("member");
        Long memberId = null;

        if(member instanceof IndividualMemberDTO) {
            memberId = ((IndividualMemberDTO) member).getId();
        } else {
            memberId = ((MemberDTO) member).getId();
        }

        model.addAttribute("aside", skillLogService.aside(memberId));
        return "skill-log/write";
    }

    @PostMapping("write")
    public RedirectView write(SkillLogDTO skillLogDTO,
                      @RequestParam("file") ArrayList<MultipartFile> multipartFiles ) {
        skillLogService.write(skillLogDTO, multipartFiles);
        log.info("{}", skillLogDTO);

        return new RedirectView("/skill-log/list");
    }

    @GetMapping("list")
    public String goToList(Model model) {
        Object member = session.getAttribute("member");
        Long memberId = null;

        if(member instanceof IndividualMemberDTO) {
            memberId = ((IndividualMemberDTO) member).getId();
        } else if(member instanceof MemberDTO) {
            memberId = ((MemberDTO) member).getId();
        }

        model.addAttribute("aside", skillLogService.aside(memberId));
        model.addAttribute("tags", tagService.selectAll());
        return "skill-log/list";
    }

    @GetMapping("detail")
    public String detail(Long id, Model model) {
        Object member = session.getAttribute("member");
        Long memberId = null;

        if(member instanceof IndividualMemberDTO) {
            memberId = ((IndividualMemberDTO) member).getId();
        } else if(member instanceof MemberDTO) {
            memberId = ((MemberDTO) member).getId();
        }

        model.addAttribute("aside", skillLogService.aside(memberId));
        model.addAttribute("skillLog", skillLogService.detail(id));
        return "skill-log/detail";
    }
}
