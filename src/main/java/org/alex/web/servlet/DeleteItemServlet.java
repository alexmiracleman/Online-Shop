package org.alex.web.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.alex.entity.Item;
import org.alex.service.ItemService;
import org.alex.web.util.PageGenerator;
import org.alex.service.SecurityService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteItemServlet extends HttpServlet {
    private final ItemService itemService;
    PageGenerator pageGenerator = PageGenerator.instance();

    public DeleteItemServlet(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

            List<Item> items = itemService.findAll();
            Map<String, Object> parameters = Map.of("items", items);
            String page = pageGenerator.getPage("remove_item.html", parameters);
            resp.getWriter().write(page);
        }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Item item = getItemFromRequest(req);
            itemService.delete(item);
            String successMessage = "THE SELECTED ITEM HAS BEEN SUCCESSFULLY REMOVED";
            List<Item> items = itemService.findAll();
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("successMessage", successMessage);
            parameters.put("items", items);
            String page = pageGenerator.getPage("remove_item.html", parameters);
            resp.getWriter().write(page);
        } catch (Exception e) {
            List<Item> items = itemService.findAll();
            String errorMessage = "NOTHING HAS BEEN REMOVED";
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("errorMessage", errorMessage);
            parameters.put("items", items);
            String page = pageGenerator.getPage("remove_item.html", parameters);
            resp.getWriter().write(page);

        }
    }

    private Item getItemFromRequest(HttpServletRequest request) {
        return Item.builder()
                .name(request.getParameter("name"))
                .build();

    }
}
