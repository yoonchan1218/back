package com.app.trycatch.service.skilllog;

import com.app.trycatch.common.enumeration.file.FileContentType;
import com.app.trycatch.common.exception.ExperienceProgramNotFoundException;
import com.app.trycatch.common.exception.SkillLogNotFoundException;
import com.app.trycatch.common.pagination.Criteria;
import com.app.trycatch.common.search.Search;
import com.app.trycatch.domain.skilllog.SkillLogLikeVO;
import com.app.trycatch.domain.skilllog.TagVO;
import com.app.trycatch.dto.experience.ExperienceProgramDTO;
import com.app.trycatch.dto.experience.ExperienceProgramFileDTO;
import com.app.trycatch.dto.file.FileDTO;
import com.app.trycatch.dto.skilllog.*;
import com.app.trycatch.repository.experience.ExperienceProgramDAO;
import com.app.trycatch.repository.experience.ExperienceProgramFileDAO;
import com.app.trycatch.repository.file.FileDAO;
import com.app.trycatch.repository.skilllog.SkillLogDAO;
import com.app.trycatch.repository.skilllog.SkillLogFileDAO;
import com.app.trycatch.repository.skilllog.SkillLogLikeDAO;
import com.app.trycatch.repository.skilllog.TagDAO;
import com.app.trycatch.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SkillLogService {
    private final SkillLogDAO skillLogDAO;
    private final TagDAO tagDAO;
    private final SkillLogFileDAO skillLogFileDAO;
    private final FileDAO fileDAO;
    private final SkillLogLikeDAO skillLogLikeDAO;

    private final ExperienceProgramDAO experienceProgramDAO;
    private final ExperienceProgramFileDAO experienceProgramFileDAO;

//    작성
    public void write(SkillLogDTO skillLogDTO, List<MultipartFile> multipartFiles) {
        String rootPath = "C:/file/";
        String todayPath = getTodayPath();
        String path = rootPath + todayPath;

        FileDTO fileDTO = new FileDTO();
        SkillLogFileDTO skillLogFileDTO = new SkillLogFileDTO();

        skillLogDAO.save(skillLogDTO);

        skillLogDTO.getTags().forEach((tagDTO) -> {
        tagDTO.setSkillLogId(skillLogDTO.getId());
            tagDAO.save(tagDTO.toVO());
        });

        skillLogFileDTO.setSkillLogId(skillLogDTO.getId());
        multipartFiles.forEach(multipartFile -> {
            if(multipartFile.getOriginalFilename().isEmpty()){
                return;
            }
            UUID uuid = UUID.randomUUID();
            fileDTO.setFilePath(todayPath);
            fileDTO.setFileSize(String.valueOf(multipartFile.getSize()));
            fileDTO.setFileOriginalName(multipartFile.getOriginalFilename());
            fileDTO.setFileName(uuid.toString() + "_" + multipartFile.getOriginalFilename());
            fileDTO.setFileContentType(multipartFile.getContentType().contains("image") ? FileContentType.IMAGE : FileContentType.OTHER);
            fileDAO.save(fileDTO);

            skillLogFileDTO.setId(fileDTO.getId());
            skillLogFileDAO.save(skillLogFileDTO.toSkillLogFileVO());

            File directory = new File(path);
            if(!directory.exists()){
                directory.mkdirs();
            }

            try {
                multipartFile.transferTo(new File(path, fileDTO.getFileName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

//    - 최근 공고
    public ExperienceProgramWithPagingDTO recentExperienceLogs(Long id, int page, Search search) {
        ExperienceProgramWithPagingDTO experienceProgramWithPagingDTO = new ExperienceProgramWithPagingDTO();
        Criteria criteria = new Criteria(page, experienceProgramDAO.findTotalByMemberIdOfChallenger(search, id));

        List<ExperienceProgramDTO> experiencePrograms = experienceProgramDAO.findAllByMemberIdOfChallenger(criteria, search, id);

        criteria.setHasMore(experiencePrograms.size() > criteria.getRowCount());
        experienceProgramWithPagingDTO.setCriteria(criteria);

        if(criteria.isHasMore()) {
            experiencePrograms.remove(experiencePrograms.size() - 1);
        }

        experiencePrograms.forEach((experienceProgramDTO) -> {
            List<ExperienceProgramFileDTO> experienceProgramFiles = experienceProgramFileDAO.findAllByExperienceProgramId(experienceProgramDTO.getId());
            experienceProgramDTO.setExperienceProgramFiles(experienceProgramFiles);
        });
        experienceProgramWithPagingDTO.setExperienceProgramLogs(experiencePrograms);

        return experienceProgramWithPagingDTO;
    }

//    aside
    public SkillLogAsideDTO aside(Long id) {
        return skillLogDAO.findProfileByMemberId(id);
    }

//    목록
    public SkillLogWithPagingDTO list(int page, Search search) {
        SkillLogWithPagingDTO skillLogWithPagingDTO = new SkillLogWithPagingDTO();
        Criteria criteria = new Criteria(page, skillLogDAO.findTotal(search));

        List<SkillLogDTO> skillLogs = skillLogDAO.findAll(criteria, search);

        criteria.setHasMore(skillLogs.size() > criteria.getRowCount());
        skillLogWithPagingDTO.setCriteria(criteria);

        if(criteria.isHasMore()){
            skillLogs.remove(skillLogs.size() - 1);
        }

        skillLogs.forEach((skillLogDTO) -> {
            skillLogDTO.setCreatedDatetime(DateUtils.toRelativeTime(skillLogDTO.getCreatedDatetime()));
            skillLogDTO.setTags(tagDAO.findAllBySkillLogId(skillLogDTO.getId())
                    .stream().map((tagVO) -> toTagDTO(tagVO)).collect(Collectors.toList()));
            skillLogDTO.setSkillLogFiles(skillLogFileDAO.findAllBySkillLogId(skillLogDTO.getId()));
        });
        skillLogWithPagingDTO.setSkillLogs(skillLogs);

        return skillLogWithPagingDTO;
    }

//    조회
    public SkillLogDTO detail(Long id, Long memberId) {
        Optional<SkillLogDTO> foundSkillLog = null;
        SkillLogDTO skillLogDTO = null;
        Optional<ExperienceProgramDTO> foundExperienceProgram = null;
        ExperienceProgramDTO experienceProgramDTO = null;
        SkillLogLikeDTO skillLogLikeDTO = new SkillLogLikeDTO();

//        skillLog
        String formattedDate = null;
        boolean updateCheck = false;

        skillLogDAO.setSkillLogViewCount(id);

        foundSkillLog = skillLogDAO.findById(id);
        skillLogDTO = foundSkillLog.orElseThrow(SkillLogNotFoundException::new);

        updateCheck = !skillLogDTO.getCreatedDatetime().equals(skillLogDTO.getUpdatedDatetime());
        formattedDate = skillLogDTO.getCreatedDatetime().split(" ")[0] + (updateCheck ? " (수정됨)" : "");
        
        skillLogDTO.setCreatedDatetime(formattedDate);
        skillLogDTO.setTags(tagDAO.findAllBySkillLogId(skillLogDTO.getId())
                .stream().map((tagVO) -> toTagDTO(tagVO)).collect(Collectors.toList()));
        skillLogDTO.setSkillLogFiles(skillLogFileDAO.findAllBySkillLogId(skillLogDTO.getId()));

//        experienceProgram
        if(skillLogDTO.getExperienceProgramId() != null){
            foundExperienceProgram = experienceProgramDAO.findById(skillLogDTO.getExperienceProgramId());
            experienceProgramDTO = foundExperienceProgram.orElseThrow(ExperienceProgramNotFoundException::new);
            experienceProgramDTO.setExperienceProgramFiles(experienceProgramFileDAO.findAllByExperienceProgramId(skillLogDTO.getExperienceProgramId()));
            skillLogDTO.setExperienceProgram(experienceProgramDTO);
        }

//        likes
        skillLogLikeDTO.setSkillLogId(skillLogDTO.getId());
        skillLogLikeDTO.setMemberId(memberId);
        skillLogDTO.setLikeCount(skillLogLikeDAO.findCountBySkillLogId(skillLogDTO.getId()));
        skillLogDTO.setLiked(skillLogLikeDAO.findBySkillLogIdAndMemberId(skillLogLikeDTO.toVO()).orElse(null) != null);

        return skillLogDTO;
    }

//    좋아요
    public int like(SkillLogLikeDTO skillLogLikeDTO) {
        SkillLogLikeVO skillLogLikeVO = skillLogLikeDAO.findBySkillLogIdAndMemberId(skillLogLikeDTO.toVO()).orElse(null);

        if(skillLogLikeVO != null){
            skillLogLikeDAO.delete(skillLogLikeVO.getId());
        } else {
            skillLogLikeDAO.save(skillLogLikeDTO.toVO());
        }

        return skillLogLikeDAO.findCountBySkillLogId(skillLogLikeDTO.getSkillLogId());
    }


    public TagDTO toTagDTO(TagVO tagVO) {
        TagDTO tagDTO = new TagDTO();

        tagDTO.setId(tagVO.getId());
        tagDTO.setSkillLogId(tagVO.getSkillLogId());
        tagDTO.setTagName(tagVO.getTagName());

        return tagDTO;
    }

    public String getTodayPath(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
