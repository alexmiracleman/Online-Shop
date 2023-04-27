package org.alex.web.servlet;

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

public class AddNewItemServlet extends HttpServlet {
    private ItemService itemService;

    public AddNewItemServlet(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Item> items = itemService.findAll();
        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String, Object> parameters = Map.of("items", items);
        String page = pageGenerator.getPage("add_item.html", parameters);
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Item item = getItemsFromRequest(req);

        itemService.add(item);
        String successMessage = "THE ITEM HAS BEEN ADDED TO YOUR CART";
        List<Item> items = itemService.findAll();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("successMessage", successMessage);
        parameters.put("items", items);
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("add_item.html", parameters);
        resp.getWriter().write(page);
        } catch (Exception e) {
        List<Item> items = itemService.findAll();
        String errorMessage1 = "THE SELECTED ITEM IS ALREADY IN YOUR CART.";
        String errorMessage2 = "IN ORDER TO UPDATE THE QUANTITY, PLEASE USE THE EDIT BUTTON IN THE MAIN MENU";
        PageGenerator pageGenerator = PageGenerator.instance();
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("errorMessage1", errorMessage1);
        parameters.put("errorMessage2", errorMessage2);
        parameters.put("items", items);;
        String page = pageGenerator.getPage("add_item.html", parameters);
        resp.getWriter().write(page);

        }
    }
    private Item getItemsFromRequest(HttpServletRequest request) {

        return Item.builder()
                .name(request.getParameter("name"))
                .quantity(Integer.parseInt(request.getParameter("quantity")))
                .build();

    }
}
