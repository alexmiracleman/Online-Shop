package org.alex.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alex.service.SecurityService;
import org.alex.web.util.Session;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SecurityFilter implements Filter {

    private SecurityService securityService;
    private List<String> openPaths = List.of("/login", "/logout", "/register");
    private int sessionTimeToLive;

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
        String actualToken = cookieValue(httpServletRequest);
        if (actualToken == null) {
            httpServletResponse.sendRedirect("/login");
        }
        Session session = securityService.getSession(actualToken);
        httpServletRequest.setAttribute("session", session);
        if (session == null) {
            httpServletResponse.sendRedirect("/login");
            return;
        }
        session.setExpireDate(LocalDateTime.now().plusMinutes(sessionTimeToLive));
        if (session.getUserType().equals("ADMIN")) {
            if (requestURI.startsWith("/admin/")) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            if (!requestURI.startsWith("/admin/")) {
                httpServletResponse.sendRedirect("/admin/items");
                return;
            }
        }
        if (session.getUserType().equals("USER")) {
            if (requestURI.startsWith("/user/")) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            if (!requestURI.startsWith("/user/")) {
                httpServletResponse.sendRedirect("/user/items");
            }
        }
    }

    private String cookieValue(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    String cookieValue = cookie.getValue();
                    return cookieValue;
                }
            }
        }
        return null;
    }
}
