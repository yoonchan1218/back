package com.app.trycatch.mapper.qna;

import com.app.trycatch.domain.qna.QnaVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaMapper {
    public void insert(QnaVO qnaVO);
    QnaVO selectById(Long id);
    List<QnaVO> selectAll();
}
