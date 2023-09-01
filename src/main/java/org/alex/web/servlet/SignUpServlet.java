package org.alex.web.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alex.entity.User;
import org.alex.service.SecurityService;
import org.alex.service.UserService;
import org.alex.util.PasswordUtils;
import org.alex.web.util.PageGenerator;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class SignUpServlet extends HttpServlet {

    private PageGenerator pageGenerator = PageGenerator.instance();
    private PasswordUtils passwordUtils;
    private SecurityService securityService;
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String page = pageGenerator.getPage("register.html", Map.of());
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

    User getUsersFromRequest(HttpServletRequest request) throws NoSuchAlgorithmException, NoSuchProviderException {
        String salt = passwordUtils.generateSalt();
        return User.builder()
                .email(request.getParameter("email"))
                .salt(salt)
                .password(passwordUtils.generateHash(request.getParameter("password"), salt))
                .userType("USER")
                .build();
    }
}
