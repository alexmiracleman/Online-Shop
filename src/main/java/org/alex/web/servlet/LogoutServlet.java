package org.alex.web.servlet;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alex.service.SecurityService;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
public class LogoutServlet extends HttpServlet {
    private SecurityService securityService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    String token = cookie.getValue();
                    securityService.logout(token);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    response.sendRedirect("/login");
                }
            }
        }
        response.sendRedirect("/login");
    }
}