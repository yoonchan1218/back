package com.app.trycatch.service.qna;

import com.app.trycatch.domain.qna.QnaCommentVO;
import com.app.trycatch.dto.qna.QnaCommentDTO;
import com.app.trycatch.repository.qna.QnaCommentDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QnaCommentService {
    private final QnaCommentDAO qnaCommentDAO;

    public Map<String, Object> write(Long memberId, Long qnaId, String content, Long parentId) {
        // 댓글(부모 없음)은 qna당 1개 제한
        if (parentId == null && qnaCommentDAO.existsByQnaIdAndMemberId(qnaId, memberId)) {
            return Map.of("success", false, "message", "이미 댓글을 작성하셨습니다.");
        }
        QnaCommentVO vo = QnaCommentVO.builder()
                .qnaId(qnaId)
                .individualMemberId(memberId)
                .qnaCommentParent(parentId)
                .qnaCommentContent(content)
                .build();
        qnaCommentDAO.save(vo);
        return Map.of("success", true);
    }

    public List<QnaCommentDTO> list(Long qnaId) {
        return qnaCommentDAO.findByQnaId(qnaId);
    }

    public void delete(Long id, Long memberId) {
        qnaCommentDAO.delete(id, memberId);
    }

    public void update(Long id, Long memberId, String content) {
        QnaCommentVO vo = QnaCommentVO.builder()
                .id(id)
                .individualMemberId(memberId)
                .qnaCommentContent(content)
                .build();
        qnaCommentDAO.update(vo);
    }
}
