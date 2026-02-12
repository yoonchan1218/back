package com.app.trycatch.mapper.file;

import com.app.trycatch.dto.file.FileDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    void insert(FileDTO fileDTO);
}
