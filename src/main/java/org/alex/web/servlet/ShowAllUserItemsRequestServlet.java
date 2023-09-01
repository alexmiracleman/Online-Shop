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

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ShowAllUserItemsRequestServlet extends HttpServlet {
    private ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Item> items = itemService.findAll();
        PageGenerator pageGenerator = PageGenerator.instance();
        Map<String, Object> parameters = Map.of("items", items);
        String page = pageGenerator.getPage("user_items_list.html", parameters);
        resp.getWriter().write(page);
    }
}
