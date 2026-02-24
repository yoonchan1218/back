package com.app.trycatch.interceptor;

import com.app.trycatch.service.Alarm.IndividualAlramService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
public class IndividualAlramInterceptor implements HandlerInterceptor {
    private final IndividualAlramService individualAlramService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("alrams", "");
        return true;
    }
}
