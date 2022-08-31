package com.xiaokong.reggie.interceptor;

import com.alibaba.fastjson.JSON;
import com.xiaokong.reggie.common.R;
import com.xiaokong.reggie.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName LoginInterceptor
 * Description LookMe
 * Author Mrk
 * Date 2022/8/22 9:35
 * Version 1.0
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        Object employee = request.getSession().getAttribute("employee");
        Long id = (Long) employee;
        BaseContext.setCurrentId(id);
        if (employee == null) {
            response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
