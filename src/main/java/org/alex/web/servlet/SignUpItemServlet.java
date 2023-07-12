package org.alex.web.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.alex.entity.Salt;
import org.alex.entity.User;
import org.alex.service.SaltService;
import org.alex.service.SecurityService;
import org.alex.service.UserService;
import org.alex.web.util.PageGenerator;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;

public class SignUpItemServlet extends HttpServlet {

    PageGenerator pageGenerator = PageGenerator.instance();

    SecurityService securityService;
    UserService userService;
    SaltService saltService;

    public SignUpItemServlet(SecurityService securityService, UserService userService, SaltService saltService) {
        this.securityService = securityService;
        this.userService = userService;
        this.saltService = saltService;
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
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            if (!securityService.emailAndPasswordNotNullCheck(email, password)) {
                String errorMessage = "The email or password cannot be empty";
                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("errorMessage", errorMessage);
                String page = pageGenerator.getPage("register.html", parameters);
                resp.getWriter().write(page);
            } else if (!securityService.emailDuplicateCheck(email)) {
                String errorMessage = "The email you entered is already registered, please proceed to log in page";
                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("errorMessage", errorMessage);
                String page = pageGenerator.getPage("register.html", parameters);
                resp.getWriter().write(page);
            } else {
                Salt salt = getSaltFromRequest(req);
                saltService.add(salt);
                User user = getUsersFromRequest(req);
                userService.add(user);

                String successMessage = "CONGRATULATIONS. YOU'RE NOW REGISTERED. PLEASE LOG IN WITH YOUR EMAIL AND PASSWORD";
                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("successMessage", successMessage);
                String page = pageGenerator.getPage("login.html", parameters);
                resp.getWriter().write(page);


            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }

    }

    Salt getSaltFromRequest(HttpServletRequest request) throws NoSuchAlgorithmException, NoSuchProviderException {
        return Salt.builder()
                .email(request.getParameter("email"))
                .passSalt(securityService.generateSalt())
                .build();
    }
    User getUsersFromRequest(HttpServletRequest request) throws NoSuchAlgorithmException, NoSuchProviderException {

        return User.builder()
                .email(request.getParameter("email"))
                .password(securityService.getHashedPassword(request.getParameter("password")) + securityService.getSalt(request.getParameter("email")))
                .build();

    }



}
