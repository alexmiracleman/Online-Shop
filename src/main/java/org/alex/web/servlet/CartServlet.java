package org.alex.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alex.entity.Item;
import org.alex.entity.ItemDepartmentType;
import org.alex.service.ItemService;
import org.alex.web.util.PageGenerator;
import org.alex.web.util.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class CartServlet extends HttpServlet {

    private ItemService itemService;
    private PageGenerator pageGenerator = PageGenerator.instance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = (Session) req.getAttribute("session");
        List<Item> items = session.getCart();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("itemDepartmentTypes", ItemDepartmentType.values());
        parameters.put("items", items);
        String page = pageGenerator.getPage("user_cart.html", parameters);
        resp.getWriter().write(page);
    }
}
