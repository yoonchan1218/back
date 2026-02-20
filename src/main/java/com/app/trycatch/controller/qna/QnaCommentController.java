package com.app.trycatch.controller.qna;

import com.app.trycatch.dto.member.IndividualMemberDTO;
import com.app.trycatch.dto.member.MemberDTO;
import com.app.trycatch.dto.qna.QnaCommentDTO;
import com.app.trycatch.service.qna.QnaCommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/qna/comment")
@RequiredArgsConstructor
public class QnaCommentController {
    private final QnaCommentService qnaCommentService;
    private final HttpSession session;

    private Long extractMemberId(Object member) {
        if (member instanceof MemberDTO m) return m.getId();
        if (member instanceof IndividualMemberDTO m) return m.getId();
        return null;
    }

    @GetMapping("/list")
    public List<QnaCommentDTO> list(@RequestParam Long qnaId) {
        return qnaCommentService.list(qnaId);
    }

    @PostMapping("/write")
    public Map<String, Object> write(@RequestParam Long qnaId,
                                     @RequestParam String qnaCommentContent,
                                     @RequestParam(required = false) Long qnaCommentParent) {
        Long memberId = extractMemberId(session.getAttribute("member"));
        if (memberId == null) return Map.of("success", false, "message", "로그인이 필요합니다.");
        return qnaCommentService.write(memberId, qnaId, qnaCommentContent, qnaCommentParent);
    }

    @DeleteMapping("/delete")
    public Map<String, Object> delete(@RequestParam Long id) {
        Long memberId = extractMemberId(session.getAttribute("member"));
        if (memberId == null) return Map.of("success", false, "message", "로그인이 필요합니다.");
        qnaCommentService.delete(id, memberId);
        return Map.of("success", true);
    }

    @PutMapping("/update")
    public Map<String, Object> update(@RequestParam Long id,
                                      @RequestParam String qnaCommentContent) {
        Long memberId = extractMemberId(session.getAttribute("member"));
        if (memberId == null) return Map.of("success", false, "message", "로그인이 필요합니다.");
        qnaCommentService.update(id, memberId, qnaCommentContent);
        return Map.of("success", true);
    }
}
