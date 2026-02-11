package com.app.trycatch.controller.qna;

import com.app.trycatch.domain.qna.QnaVO;
import com.app.trycatch.dto.qna.QnaDTO;
import com.app.trycatch.service.qna.QnaService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
@Slf4j
public class QnaController {
    private final HttpSession session;
    private final QnaService qnaService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("qnaList", qnaService.list());
        return "qna/QnA";
    }

    @GetMapping("/detail")
    public String detail(Long id, Model model) {
        model.addAttribute("qna", qnaService.detail(id));
        return "qna/QnA-detail";
    }

    @GetMapping("/write")
    public String goToWriteForm() {
        return "qna/write";
    }

    @PostMapping("/write")
    public RedirectView write(QnaVO qnaVO) {
//        MemberDTO member = (MemberDTO) session.getAttribute("member");
//        qnaDTO.setIndividualMemberId(member.getId());
        qnaService.write(qnaVO);
        return new RedirectView("/qna/list");
    }
}
