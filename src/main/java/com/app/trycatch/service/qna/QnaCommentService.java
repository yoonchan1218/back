package com.app.trycatch.service.qna;

import com.app.trycatch.dto.qna.QnaCommentDTO;
import com.app.trycatch.repository.qna.QnaCommentDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaCommentService {
    private final QnaCommentDAO qnaCommentDAO;

    public void write(QnaCommentDTO qnaCommentDTO) {
        qnaCommentDAO.save(qnaCommentDTO);
    }

    public List<QnaCommentDTO> list(Long qnaId) {
        return qnaCommentDAO.findByQnaId(qnaId);
    }

    public void delete(Long id) {
        qnaCommentDAO.delete(id);
    }
}
