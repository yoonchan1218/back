package com.app.trycatch.mapper.qna;

import com.app.trycatch.domain.qna.QnaCommentVO;
import com.app.trycatch.dto.qna.QnaCommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaCommentMapper {
    void insert(QnaCommentVO qnaCommentVO);
    List<QnaCommentDTO> selectByQnaId(Long qnaId);
    void delete(Long id);
}
