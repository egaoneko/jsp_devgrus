package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Description
 * Donghyun Seo (egaoneko@naver.com)
 * 2015-01-20
 * Copyright ⓒ 2013-2015 Donghyun Seo All rights reserved.
 * version
 */
public class LoginCheckFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpSession session = httpServletRequest.getSession(false);

        boolean login = false;
        if(session != null){
            if(session.getAttribute("MEMBER") != null){
                login = true;
            }
        }
        if(login){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/loginForm.jsp");
            dispatcher.forward(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {}
}
