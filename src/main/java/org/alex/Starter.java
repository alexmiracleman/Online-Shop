package org.alex;


import org.alex.dao.jdbc.JdbcItemDao;
import org.alex.service.ItemService;
import org.alex.web.servlet.AddNewItemServlet;
import org.alex.web.servlet.DeleteItemServlet;
import org.alex.web.servlet.ShowAllItemsRequestServlet;
import org.alex.web.servlet.UpdateItemServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {
    public static void main(String[] args) throws Exception {
        //dao
        JdbcItemDao jdbcSolutionDao = new JdbcItemDao();

        //service
        ItemService itemService = new ItemService(jdbcSolutionDao);
        //servlet
        ShowAllItemsRequestServlet showAllItemsRequestServlet = new ShowAllItemsRequestServlet(itemService);
        AddNewItemServlet addNewItemServlet = new AddNewItemServlet(itemService);
        DeleteItemServlet deleteItemServlet = new DeleteItemServlet(itemService);
        UpdateItemServlet updateItemServlet = new UpdateItemServlet(itemService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(showAllItemsRequestServlet), "/cart");
        context.addServlet(new ServletHolder(addNewItemServlet), "/cart/add");
        context.addServlet(new ServletHolder(deleteItemServlet), "/cart/delete");
        context.addServlet(new ServletHolder(updateItemServlet), "/cart/update");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();

    }
}
