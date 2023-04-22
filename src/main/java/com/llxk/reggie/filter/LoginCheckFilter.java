package com.llxk.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.llxk.reggie.common.BaseContext;
import com.llxk.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: LoginCheckFilter
 * Package: com.llxk.reggie.filter
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc 检查用户是否已经登录
 * @Date 2023/4/17 20:32
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    /**
     * @PATH_MATCHER 路径匹配器，支持通配符
     */
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info("拦截到请求:{}", request.getRequestURI());

        //1.获取本次请求的URI
        String requestURI = request.getRequestURI();
        //定义不需要处理的路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",//移动端发送短信
                "/user/login"//移动端登录
        };

        //2.判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //3.如果不需要处理，则直接放行
        if(check){
            log.info("本次请求{}不需要被处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        //4-1.判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("employee") != null) {

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            log.info("用户已登录，用户ID为：{}", request.getSession().getAttribute("employee"));
            filterChain.doFilter(request, response);
            return;
        }

        //4-2.判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("user") != null) {

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            log.info("用户已登录，用户ID为：{}", userId);
            filterChain.doFilter(request, response);
            return;
        }


        //5.如果未登录则返回未登录结果
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls 放行路径
     * @param requestURI 待匹配路径
     * @return true：放行；false：拦截
     */
    public boolean check(String[] urls, String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
