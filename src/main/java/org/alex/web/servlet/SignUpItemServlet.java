package org.alex.web.servlet;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.alex.entity.User;
import org.alex.service.SecurityService;
import org.alex.service.UserService;
import org.alex.web.util.PageGenerator;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SignUpItemServlet extends HttpServlet {

    PageGenerator pageGenerator = PageGenerator.instance();

    List<String> userTokens;
    SecurityService securityService;
    UserService userService;

    public SignUpItemServlet(List<String> userTokens, SecurityService securityService, UserService userService) {
        this.userTokens = userTokens;
        this.securityService = securityService;
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> parameters = new HashMap<>();
        String page = pageGenerator.getPage("register.html", parameters);
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            if (securityService.emailDuplicateCheck(req)) {
                User user = getUsersFromRequest(req);
                userService.add(user);
                String userToken = UUID.randomUUID().toString();
                System.out.println("User Token: " + userToken);
                userTokens.add(userToken);
                Cookie cookie = new Cookie("user-token", userToken);
                resp.addCookie(cookie);
                resp.addCookie(new Cookie("preferredLang", "ua"));
                resp.sendRedirect("/");
            } else {
                String errorMessage = "The email you entered is already registered";
                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("errorMessage", errorMessage);
                String page = pageGenerator.getPage("register.html", parameters);
                resp.getWriter().write(page);
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }

    }

    User getUsersFromRequest(HttpServletRequest request) throws NoSuchAlgorithmException, NoSuchProviderException {
        return User.builder()
                .email(request.getParameter("email"))
                .password(securityService.getHashedPassword(request.getParameter("password")))
                .build();

    }

}
