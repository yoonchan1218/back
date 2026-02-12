package com.app.trycatch.controller.qna;

import com.app.trycatch.domain.qna.QnaVO;
import com.app.trycatch.mapper.qna.QnaJobCategoryMapper;
import com.app.trycatch.service.qna.QnaService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {
    private final HttpSession session;
    private final QnaService qnaService;
    private final QnaJobCategoryMapper qnaJobCategoryMapper;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("qnaWithPaging", qnaService.list(page));
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
    public RedirectView write(
            @RequestParam(required = false) String qnaTitle,
            @RequestParam(required = false) String qnaContent,
            @RequestParam(required = false) Long jobCategorySmallCode,
            @RequestParam(required = false) String jobCategoryName,
            @RequestParam(value = "file", required = false) ArrayList<MultipartFile> files
    ) {
        // TODO: 세션 연동 후 1L → member.getId() 로 교체
        Long jobCategorySmallId = null;
        if (jobCategorySmallCode != null) {
            jobCategorySmallId = qnaJobCategoryMapper.selectIdByCode(jobCategorySmallCode);
        }
        QnaVO qnaVO = QnaVO.builder()
                .individualMemberId(1L)
                .qnaTitle(qnaTitle)
                .qnaContent(qnaContent)
                .jobCategorySmallId(jobCategorySmallId)
                .jobCategoryName(jobCategoryName)
                .build();
        qnaService.write(qnaVO, files != null ? files : new ArrayList<>());
        return new RedirectView("/qna/detail?id=" + qnaVO.getId());
    }
}
