package com.app.trycatch.repository.qna;

import com.app.trycatch.dto.qna.QnaCommentDTO;
import com.app.trycatch.mapper.qna.QnaCommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QnaCommentDAO {
    private final QnaCommentMapper qnaCommentMapper;

    public void save(QnaCommentDTO qnaCommentDTO) {
        qnaCommentMapper.insert(qnaCommentDTO.toQnaCommentVO());
    }

    public List<QnaCommentDTO> findByQnaId(Long qnaId) {
        return qnaCommentMapper.selectByQnaId(qnaId);
    }

    public void delete(Long id) {
        qnaCommentMapper.delete(id);
    }
}
