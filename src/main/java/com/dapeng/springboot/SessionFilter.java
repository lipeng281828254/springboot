package com.dapeng.springboot;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author lipeng
 * @version 1.0
 * @date 2020/3/19 2:36
 * @message：过滤器
 */
@Slf4j
@WebFilter(filterName = "sessionFilter",urlPatterns = {"/*"})
public class SessionFilter implements Filter {

    //标示符：表示当前用户未登录(可根据自己项目需要改为json样式)
    String NO_LOGIN = "您还未登录";

    //不需要登录就可以访问的路径(比如:注册登录等)
    String[] includeUrls = new String[]{"/api/userInfo/addUserInfo.json", "/api/userInfo/login.json"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        log.info("filter url:{}", uri);

        //是否需要过滤
        boolean needFilter = isNeedFilter(uri);
        if (!needFilter) { //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } else { //需要过滤器
            // session中包含user对象,则是登录状态
            if (session != null && session.getAttribute("userInfo") != null) {
                // System.out.println("user:"+session.getAttribute("user"));
                log.info("user--->>>{}", session.getAttribute("userInfo"));
                filterChain.doFilter(request, response);
            } else {
                response.getWriter().write(this.NO_LOGIN);
//                String requestType = request.getHeader("X-Requested-With");
//                //判断是否是ajax请求
//                if (requestType != null && "XMLHttpRequest".equals(requestType)) {
//
//                } else {
//                    //重定向到登录页(需要在static文件夹下建立此html文件)
//                    response.sendRedirect(request.getContextPath() + "/user/login.html");
//                }
                return;
            }

        }
    }

    @Override
    public void destroy() {

    }


    public boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
            if (includeUrl.equals(uri)) {
                return false;
            }
        }

        return true;
    }

}
