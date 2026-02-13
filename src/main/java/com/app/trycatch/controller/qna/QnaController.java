package com.app.trycatch.controller.qna;

import com.app.trycatch.domain.qna.QnaVO;
import com.app.trycatch.dto.qna.CorpNameKeywordDTO;
import com.app.trycatch.mapper.qna.QnaJobCategoryMapper;
import com.app.trycatch.mapper.qna.QnaMapper;
import com.app.trycatch.service.qna.QnaService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {
    private final HttpSession session;
    private final QnaService qnaService;
    private final QnaJobCategoryMapper qnaJobCategoryMapper;
    private final QnaMapper qnaMapper;

    @GetMapping("/search-company")
    @ResponseBody
    public List<CorpNameKeywordDTO> searchCompany(@RequestParam String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return List.of();
        return qnaMapper.selectCorpByKeyword(keyword.trim());
    }

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("qnaWithPaging", qnaService.list(page));
        // TODO: 세션 연동 후 session.getAttribute("loginMember") 값이 채워지면 자동으로 로그인 UI 전환
        model.addAttribute("loginMember", session.getAttribute("loginMember"));
        return "qna/QnA";
    }

    @GetMapping("/detail")
    public String detail(Long id, Model model) {
        model.addAttribute("qna", qnaService.detail(id));
        model.addAttribute("loginMember", session.getAttribute("loginMember"));
        return "qna/QnA-detail";
    }

    @GetMapping("/write")
    public String goToWriteForm(Model model) {
        model.addAttribute("loginMember", session.getAttribute("loginMember"));
        return "qna/write";
    }

    @PostMapping("/write")
    public RedirectView write(
            @RequestParam(required = false) String qnaTitle,
            @RequestParam(required = false) String qnaContent,
            @RequestParam(required = false) Long jobCategorySmallCode,
            @RequestParam(required = false) String jobCategoryName,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String collegeFriend,
            @RequestParam(value = "file", required = false) ArrayList<MultipartFile> files
    ) {
        // TODO: 세션 연동 후 1L → member.getId() 로 교체
        Long jobCategorySmallId = null;
        if (jobCategorySmallCode != null) {
            jobCategorySmallId = qnaJobCategoryMapper.selectIdByCode(jobCategorySmallCode);
        }
        QnaVO qnaVO = QnaVO.builder()
                .individualMemberId(4L)
                .qnaTitle(qnaTitle)
                .qnaContent(qnaContent)
                .jobCategorySmallId(jobCategorySmallId)
                .jobCategoryName(jobCategoryName)
                .companyName(companyName)
                .collegeFriend(collegeFriend)
                .build();
        qnaService.write(qnaVO, files != null ? files : new ArrayList<>());
        return new RedirectView("/qna/detail?id=" + qnaVO.getId());
    }
}
