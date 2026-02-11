package com.app.trycatch.controller.skilllog;

import com.app.trycatch.dto.skilllog.SkillLogDTO;
import com.app.trycatch.service.skilllog.SkillLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/skill-log/**")
@RequiredArgsConstructor
@Slf4j
public class SkillLogController {
    private final SkillLogService skillLogService;

    @GetMapping("write")
    public String goToWrite(SkillLogDTO skillLogDTO){
        return "skill-log/log-write";
    }

    @PostMapping("write")
    public void write(SkillLogDTO skillLogDTO) {
        skillLogDTO.setMemberId(1L);
        skillLogService.write(skillLogDTO);
    }
}
