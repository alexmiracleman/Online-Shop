package org.alex.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.alex.entity.Item;
import org.alex.service.ItemService;
import org.alex.web.util.PageGenerator;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateItemServlet extends HttpServlet {
    private ItemService itemService;

    public UpdateItemServlet(ItemService itemService) {
        this.itemService = itemService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Item> items = itemService.findAll();
        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String, Object> parameters = Map.of("items", items);
        String page = pageGenerator.getPage("update_item.html", parameters);
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Item item = getItemFromRequest(req);

            itemService.update(item);
            String successMessage = "THE SELECTED ITEM HAS BEEN SUCCESSFULLY UPDATED IN YOUR CART";
            List<Item> items = itemService.findAll();
            PageGenerator pageGenerator = PageGenerator.instance();
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("successMessage", successMessage);
            parameters.put("items", items);
            String page = pageGenerator.getPage("update_item.html", parameters);
            resp.getWriter().write(page);
        } catch (Exception e) {
            List<Item> items = itemService.findAll();
            String errorMessage = "NOTHING WAS UPDATED";
            PageGenerator pageGenerator = PageGenerator.instance();
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("errorMessage", errorMessage);
            parameters.put("items", items);
            String page = pageGenerator.getPage("update_item.html", parameters);
            resp.getWriter().write(page);

        }
    }

    private Item getItemFromRequest(HttpServletRequest request) {

        return Item.builder()
                .quantity(Integer.parseInt(request.getParameter("quantity")))
                .name(request.getParameter("name"))
                .build();

    }
}