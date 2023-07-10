package org.alex.web.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.alex.entity.Item;
import org.alex.entity.ItemDepartmentType;
import org.alex.service.ItemService;
import org.alex.web.util.PageGenerator;
import org.alex.service.SecurityService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AddNewItemServlet extends HttpServlet {
    private final ItemService itemService;

    PageGenerator pageGenerator = PageGenerator.instance();


    public AddNewItemServlet(ItemService itemService) {
        this.itemService = itemService;

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

            List<Item> items = itemService.findAll();
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("itemDepartmentTypes", ItemDepartmentType.values());
            parameters.put("items", items);
            String page = pageGenerator.getPage("add_item.html", parameters);
            resp.getWriter().write(page);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Item item = getItemsFromRequest(req);
            itemService.add(item);
            String successMessage = "THE ITEM HAS BEEN ADDED";
            List<Item> items = itemService.findAll();
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("itemDepartmentTypes", ItemDepartmentType.values());
            parameters.put("successMessage", successMessage);
            parameters.put("items", items);
            String page = pageGenerator.getPage("add_item.html", parameters);
            resp.getWriter().write(page);
        } catch (Exception e) {
            List<Item> items = itemService.findAll();
            String errorMessage = "NOTHING HAVE BEEN ADDED";
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("itemDepartmentTypes", ItemDepartmentType.values());
            parameters.put("errorMessage", errorMessage);
            parameters.put("items", items);
            String page = pageGenerator.getPage("add_item.html", parameters);
            resp.getWriter().write(page);

        }
    }

    private Item getItemsFromRequest(HttpServletRequest request) {
        String department = request.getParameter("department");
        return Item.builder()
                .name(request.getParameter("name"))
                .price(Integer.parseInt(request.getParameter("price")))
                .itemDepartmentType(ItemDepartmentType.getById(department))
                .build();

    }
}
