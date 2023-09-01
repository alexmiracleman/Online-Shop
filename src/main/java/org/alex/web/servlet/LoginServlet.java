package org.alex.web.servlet;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.alex.entity.Credentials;
import org.alex.service.SecurityService;
import org.alex.web.util.PageGenerator;
import org.alex.web.util.Session;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class LoginServlet extends HttpServlet {

    private PageGenerator pageGenerator = PageGenerator.instance();
    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = pageGenerator.getPage("login.html", Map.of());
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            Session session = securityService.authenticate(new Credentials(email, password));
            if (session != null) {
                Cookie cookie = new Cookie("user-token", session.getToken());
                cookie.setMaxAge(60 * 60 * 60 * 365);
                resp.addCookie(cookie);
                resp.sendRedirect("/user/items");
            }
            if (session == null) {
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