package com.app.trycatch.controller.member;

import com.app.trycatch.dto.member.IndividualMemberDTO;
import com.app.trycatch.dto.member.MemberDTO;
import com.app.trycatch.service.member.CorpService;
import com.app.trycatch.service.member.IndividualMemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/main/**")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final IndividualMemberService individualMemberService;
    private final CorpService corpService;
    private HttpSession session;

    @GetMapping("individual-join")
    public String goIndividualJoinForm(){
        return "main/individual-join";
    }


    @PostMapping("individual-join")
    public RedirectView individualJoin(IndividualMemberDTO individualMemberDTO){
        individualMemberService.joinIndividual(individualMemberDTO);
        return new RedirectView("/main/log-in");
    }

    @GetMapping("log-in")
    public String goLoginForm(){
        return "main/log-in";
    }

}
