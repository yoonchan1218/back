package com.app.trycatch.service.qna;

import com.app.trycatch.domain.qna.QnaVO;
import com.app.trycatch.dto.qna.QnaDTO;
import com.app.trycatch.mapper.qna.QnaMapper;
import com.app.trycatch.repository.qna.QnaDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaMapper qnaMapper;
    private final QnaDAO qnaDAO;

    public void write(QnaVO qnaVO) {
        qnaDAO.save(qnaVO);
    }

    public List<QnaDTO> list() {
        return qnaMapper.selectAll();
    }

    public QnaDTO detail(Long id) {
        qnaMapper.increaseViewCount(id);
        return qnaMapper.selectById(id);
    }
}
