package com.app.trycatch.service;

import com.app.trycatch.dto.skilllog.SkillLogReportDTO;
import com.app.trycatch.service.report.ReportService;
import com.app.trycatch.service.skilllog.SkillLogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ReportServiceTests {
    @Autowired
    private ReportService reportService;

    @Test
    public void testReport() {
        SkillLogReportDTO skillLogReportDTO = new SkillLogReportDTO();

        skillLogReportDTO.setMemberId(6L);
        skillLogReportDTO.setReportReasonCode(1);
        skillLogReportDTO.setReportReasonDetail("불건전한 내용");
        skillLogReportDTO.setSkillLogId(30L);

        reportService.report(skillLogReportDTO);
    }
}
