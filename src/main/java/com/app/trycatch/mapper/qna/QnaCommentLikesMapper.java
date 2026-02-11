package com.app.trycatch.mapper.qna;

import com.app.trycatch.domain.qna.QnaCommentLikesVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QnaCommentLikesMapper {
    public void insert (QnaCommentLikesVO qnaCommentLikesVO);
}
