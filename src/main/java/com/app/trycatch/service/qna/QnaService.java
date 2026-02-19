package com.app.trycatch.service.qna;

import com.app.trycatch.common.enumeration.file.FileContentType;
import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.domain.qna.QnaLikesVO;
import com.app.trycatch.domain.qna.QnaVO;
import com.app.trycatch.dto.file.FileDTO;
import com.app.trycatch.dto.qna.QnaDTO;
import com.app.trycatch.dto.qna.QnaWithPagingDTO;
import com.app.trycatch.mapper.qna.QnaLikesMapper;
import com.app.trycatch.mapper.qna.QnaMapper;
import com.app.trycatch.repository.file.FileDAO;
import com.app.trycatch.repository.qna.QnaDAO;
import com.app.trycatch.repository.qna.QnaFileDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaMapper qnaMapper;
    private final QnaLikesMapper qnaLikesMapper;
    private final QnaDAO qnaDAO;
    private final FileDAO fileDAO;
    private final QnaFileDAO qnaFileDAO;

    public void write(QnaVO qnaVO, ArrayList<MultipartFile> files) {
        qnaDAO.save(qnaVO);
        String todayPath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        boolean firstFile = true;
        for (MultipartFile file : files) {
            if (file.getOriginalFilename().isEmpty()) continue;
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFilePath(todayPath);
            fileDTO.setFileName(UUID.randomUUID() + "_" + file.getOriginalFilename());
            fileDTO.setFileOriginalName(file.getOriginalFilename());
            fileDTO.setFileSize(String.valueOf(file.getSize()));
            fileDTO.setFileContentType(
                file.getContentType().contains("image") ? FileContentType.IMAGE : FileContentType.OTHER
            );
            fileDAO.save(fileDTO);
            qnaFileDAO.save(fileDTO.getId(), qnaVO.getId());
            if (firstFile) {
                qnaMapper.updateFileId(qnaVO.getId(), fileDTO.getId());
                firstFile = false;
            }
            File dir = new File("C:/file/" + todayPath);
            if (!dir.exists()) dir.mkdirs();
            try {
                file.transferTo(new File(dir, fileDTO.getFileName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public QnaWithPagingDTO list(int page, int sort) {
        QnaWithPagingDTO result = new QnaWithPagingDTO();
        int total = qnaDAO.findTotal();
        Criteria criteria = new Criteria(page, total);
        List<QnaDTO> qnas = qnaDAO.findAll(criteria, sort);
        criteria.setHasMore(qnas.size() > criteria.getRowCount());
        if (criteria.isHasMore()) {
            qnas.remove(qnas.size() - 1);
        }
        result.setQnas(qnas);
        result.setCriteria(criteria);
        result.setTotal(total);
        return result;
    }

    public QnaDTO detail(Long id, Long memberId) {
        qnaMapper.increaseViewCount(id);
        QnaDTO qna = qnaMapper.selectById(id);
        if (qna != null && memberId != null) {
            qna.setLikedByCurrentUser(qnaLikesMapper.existsByMemberAndQna(memberId, id) > 0);
        }
        return qna;
    }

    public int toggleLike(Long memberId, Long qnaId) {
        if (qnaLikesMapper.existsByMemberAndQna(memberId, qnaId) > 0) {
            qnaLikesMapper.deleteByMemberAndQna(memberId, qnaId);
        } else {
            qnaLikesMapper.insert(QnaLikesVO.builder().memberId(memberId).qnaId(qnaId).build());
        }
        return qnaLikesMapper.countByQnaId(qnaId);
    }

    public void delete(Long memberId, Long qnaId) {
        QnaDTO qna = qnaMapper.selectById(qnaId);
        if (qna != null && qna.getIndividualMemberId().equals(memberId)) {
            qnaMapper.delete(qnaId);
        }
    }
}
