package org.alex.web.servlet;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.alex.service.UserService;
import org.alex.web.util.PageGenerator;
import org.alex.service.SecurityService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;

public class LoginItemServlet extends HttpServlet {

    PageGenerator pageGenerator = PageGenerator.instance();

    List<String> userTokens;
    SecurityService securityService;

    public LoginItemServlet(List<String> userTokens, SecurityService securityService) {
        this.userTokens = userTokens;
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> parameters = new HashMap<>();
        String page = pageGenerator.getPage("login.html", parameters);
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        //check if email and password are correct -> db
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            if (securityService.loginCheck(email, password)) {

                Cookie cookie = new Cookie("user-token", securityService.generateCookie());
                resp.addCookie(cookie);
                resp.sendRedirect("/items");
            } else {
                String errorMessage = "The email or password is incorrect";
                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("errorMessage", errorMessage);
                String page = pageGenerator.getPage("login.html", parameters);
                resp.getWriter().write(page);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

}
