package com.app.trycatch.repository.skilllog;

import com.app.trycatch.domain.skilllog.TagVO;
import com.app.trycatch.mapper.skilllog.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagDAO {
    private final TagMapper tagMapper;

    public void save(TagVO tagVO) {
        tagMapper.insert(tagVO);
    }
}
