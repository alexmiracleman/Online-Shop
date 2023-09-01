package org.alex.web.servlet;

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
public class RemoveItemFromCartServlet extends HttpServlet {

    private ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Session session = (Session) request.getAttribute("session");
            List<Item> cart = session.getCart();
            Item item = itemService.findByIdInCart(cart, Integer.parseInt(request.getParameter("id")));
            if (item != null) {
                cart.remove(item);
            }
            PageGenerator pageGenerator = PageGenerator.instance();
            String successMessage = "THE ITEM HAS BEEN REMOVED";
            List<Item> items = session.getCart();
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("itemDepartmentTypes", ItemDepartmentType.values());
            parameters.put("items", items);
            parameters.put("successMessage", successMessage);
            String page = pageGenerator.getPage("user_cart.html", parameters);
            response.getWriter().write(page);
        } catch (Exception e) {
            response.sendRedirect("/user/cart");
        }
    }
}