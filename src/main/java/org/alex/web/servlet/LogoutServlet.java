package org.alex.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.alex.service.SecurityService;

import java.io.IOException;
public class LogoutServlet extends HttpServlet {
    SecurityService securityService;

    public LogoutServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        securityService.cookieRemove(request);
        response.sendRedirect("/login");

    }
}