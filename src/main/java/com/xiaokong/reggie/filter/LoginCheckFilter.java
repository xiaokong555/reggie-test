package com.xiaokong.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.xiaokong.reggie.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName LoginCheckFilter
 * Description 使用过滤器检查用户是否在登录状态访问敏感页面
 * Author Mrk
 * Date 2022/8/21 11:10
 * Version 1.0
 */
// @WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher ant = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hsp = (HttpServletRequest) request;
        HttpServletResponse hsp2 = (HttpServletResponse) response;

        String uri = hsp.getRequestURI();
        String[] noUris = {"/employee/login", "/employee/logout", "/backend/**", "/front/**"};

        for (String nouri : noUris) {
            if (ant.match(nouri, uri)) {
                chain.doFilter(hsp, hsp2);
                return;
            }
        }

        Object employee = hsp.getSession().getAttribute("employee");
        if (employee != null) {
            chain.doFilter(hsp, hsp2);
            return;
        }

        hsp2.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }
}
