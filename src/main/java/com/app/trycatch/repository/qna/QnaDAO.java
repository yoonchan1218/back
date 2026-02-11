package com.app.trycatch.repository.qna;

import com.app.trycatch.domain.qna.QnaVO;
import com.app.trycatch.dto.qna.QnaDTO;
import com.app.trycatch.mapper.qna.QnaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QnaDAO {
    private final QnaMapper qnaMapper;

//    작성
    public void save(QnaVO qnaVO) {
        qnaMapper.insert(qnaVO);
    }


}
