package com.app.trycatch.service.report;

import com.app.trycatch.dto.report.ReportDTO;
import com.app.trycatch.dto.skilllog.SkillLogReportDTO;
import com.app.trycatch.repository.report.ReportDAO;
import com.app.trycatch.repository.skilllog.SkillLogReportDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ReportService {
    private final ReportDAO reportDAO;
    private final SkillLogReportDAO skillLogReportDAO;

    //    skillLog 신고
    public void report(SkillLogReportDTO skillLogReportDTO) {
        ReportDTO reportDTO = new ReportDTO();

        reportDTO.setMemberId(skillLogReportDTO.getMemberId());
        reportDTO.setReportReasonCode(skillLogReportDTO.getReportReasonCode());
        reportDTO.setReportReasonDetail(skillLogReportDTO.getReportReasonDetail());

        reportDAO.save(reportDTO);
        skillLogReportDTO.setId(reportDTO.getId());

        skillLogReportDAO.save(skillLogReportDTO.toSkillLogReportVO());
    }
}
