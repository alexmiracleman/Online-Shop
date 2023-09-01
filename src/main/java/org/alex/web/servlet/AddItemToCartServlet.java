package org.alex.web.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alex.entity.Item;
import org.alex.service.ItemService;
import org.alex.web.util.PageGenerator;
import org.alex.web.util.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddItemToCartServlet extends HttpServlet {

    private ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Session session = (Session) request.getAttribute("session");
            List<Item> cart = session.getCart();
            Item item = itemService.findById(Integer.parseInt(request.getParameter("id")));
            if (item != null) {
                cart.add(item);
            }
            List<Item> items = itemService.findAll();
            PageGenerator pageGenerator = PageGenerator.instance();
            String successMessage = "THE ITEM HAS BEEN ADDED";
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("successMessage", successMessage);
            parameters.put("items", items);
            String page = pageGenerator.getPage("user_items_list.html", parameters);
            response.getWriter().write(page);
        } catch (Exception e) {
            response.sendRedirect("/user/cart");
        }
    }
}
