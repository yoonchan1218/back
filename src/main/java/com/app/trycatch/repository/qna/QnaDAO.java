package com.app.trycatch.repository.qna;

import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.domain.qna.QnaVO;
import com.app.trycatch.dto.qna.QnaDTO;
import com.app.trycatch.mapper.qna.QnaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QnaDAO {
    private final QnaMapper qnaMapper;

//    작성
    public void save(QnaVO qnaVO) {
        qnaMapper.insert(qnaVO);
    }

//    수정
    public void update(QnaVO qnaVO) {
        qnaMapper.update(qnaVO);
    }

//    목록 (페이징)
    public List<QnaDTO> findAll(Criteria criteria, int sort, String keyword) {
        return qnaMapper.selectAll(criteria, sort, keyword);
    }

//    전체 개수
    public int findTotal(String keyword) {
        return qnaMapper.selectTotal(keyword);
    }

}
