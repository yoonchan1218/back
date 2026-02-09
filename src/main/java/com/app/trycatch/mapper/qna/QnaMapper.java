package com.app.trycatch.mapper.qna;

import com.app.trycatch.domain.qna.QnaVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaMapper {
    public void insert(QnaVO qnaVO);
}
