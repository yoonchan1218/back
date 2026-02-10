package com.app.trycatch.mapper.skilllog;

import com.app.trycatch.domain.skilllog.TagVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface TagMapper {
    public void insert(TagVO tagVO);

    public Optional<TagVO> selectByTagName(String tagName);
}
