package com.app.trycatch.controller.qna;

import com.app.trycatch.dto.qna.QnaCommentDTO;
import com.app.trycatch.service.qna.QnaCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qna/comment")
@RequiredArgsConstructor
@Slf4j
public class QnaCommentController {
    private final QnaCommentService qnaCommentService;

    @PostMapping("/write")
    public void write(QnaCommentDTO qnaCommentDTO) {
        qnaCommentService.write(qnaCommentDTO);
    }

    @PostMapping("/delete")
    public void delete(Long id) {
        qnaCommentService.delete(id);
    }
}
