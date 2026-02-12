package com.app.trycatch.repository.file;

import com.app.trycatch.dto.file.FileDTO;
import com.app.trycatch.mapper.file.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FileDAO {
    private final FileMapper fileMapper;

    public void save(FileDTO fileDTO) {
        fileMapper.insert(fileDTO);
    }
}
