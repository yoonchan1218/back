package com.app.trycatch.mapper.qna;

import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.domain.qna.QnaVO;
import com.app.trycatch.dto.qna.QnaDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaMapper {
    void insert(QnaVO qnaVO);
    QnaDTO selectById(Long id);
    List<QnaDTO> selectAll(Criteria criteria);
    int selectTotal();
    void increaseViewCount(Long id);
    void delete(Long id);
}
