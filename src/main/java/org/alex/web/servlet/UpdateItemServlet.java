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

public class UpdateItemServlet extends HttpServlet {
    public UpdateItemServlet(ItemService itemService) {
        this.itemService = itemService;
    }

    private final ItemService itemService;
    PageGenerator pageGenerator = PageGenerator.instance();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


            List<Item> items = itemService.findAll();
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("items", items);
            String page = pageGenerator.getPage("update_item.html", parameters);
            resp.getWriter().write(page);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Item item = getItemFromRequest(req);
            itemService.update(item);
            String successMessage = "THE SELECTED PRICE HAS BEEN SUCCESSFULLY UPDATED";
            List<Item> items = itemService.findAll();
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("successMessage", successMessage);
            parameters.put("items", items);
            String page = pageGenerator.getPage("update_item.html", parameters);
            resp.getWriter().write(page);
        } catch (Exception e) {
            List<Item> items = itemService.findAll();
            String errorMessage = "NOTHING WAS UPDATED";
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("errorMessage", errorMessage);
            parameters.put("items", items);
            String page = pageGenerator.getPage("update_item.html", parameters);
            resp.getWriter().write(page);

        }
    }

    private Item getItemFromRequest(HttpServletRequest request) {
        return Item.builder()
                .name(request.getParameter("name"))
                .price(Integer.parseInt(request.getParameter("price")))
                .build();

    }
}