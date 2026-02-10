package com.app.trycatch.mapper.qna;

import com.app.trycatch.domain.qna.QnaCommentVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaCommentMapper {
    public void insert(QnaCommentVO qnaCommentVO);
}
