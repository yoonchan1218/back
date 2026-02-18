package com.app.trycatch.common.exception.handler;

import com.app.trycatch.common.exception.LoginFailException;
import com.app.trycatch.common.exception.SkillLogNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice(basePackages = "com.app.trycatch.controller.skilllog")
public class SkillLogExceptionHandler {
    @ExceptionHandler(SkillLogNotFoundException.class)
    protected RedirectView skillLogNotFound(SkillLogNotFoundException skillLogNotFoundException, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return new RedirectView("/skill-log/list");
    }
}
