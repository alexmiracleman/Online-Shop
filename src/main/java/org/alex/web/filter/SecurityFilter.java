package org.alex.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.alex.service.SecurityService;

import java.io.IOException;
import java.util.List;

public class SecurityFilter implements Filter {

    private SecurityService securityService;
    private List<String> openPaths = List.of("/login", "/logout", "/register");

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String requestURI = httpServletRequest.getRequestURI();
        for (String openPath : openPaths) {
            if (requestURI.startsWith(openPath)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        if (securityService.cookieCheck(httpServletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }
}
