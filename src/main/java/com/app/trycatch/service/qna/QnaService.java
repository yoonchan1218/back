package com.app.trycatch.service.qna;

import com.app.trycatch.domain.qna.QnaVO;
import com.app.trycatch.mapper.qna.QnaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaMapper qnaMapper;

    public void writeQna(QnaVO qnaVO) {
        qnaMapper.insert(qnaVO);
    }
}