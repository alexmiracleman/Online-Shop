package org.alex;


import jakarta.servlet.DispatcherType;
import org.alex.dao.jdbc.JdbcItemDao;
import org.alex.dao.jdbc.JdbcUserDao;
import org.alex.service.ItemService;
import org.alex.service.UserService;
import org.alex.web.filter.SecurityFilter;
import org.alex.web.servlet.*;
import org.alex.service.SecurityService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class Starter {
    public static void main(String[] args) throws Exception {
        //dao
        JdbcItemDao jdbcItemDao = new JdbcItemDao();
        JdbcUserDao jdbcUserDao = new JdbcUserDao();

        //service
        ItemService itemService = new ItemService(jdbcItemDao);
        UserService userService = new UserService(jdbcUserDao);
        //servlet
        ShowAllItemsRequestServlet showAllItemsRequestServlet = new ShowAllItemsRequestServlet(itemService);
        List<String> userTokens = Collections.synchronizedList(new ArrayList<>());
        SecurityService securityService = new SecurityService(userTokens, userService);
        AddNewItemServlet addNewItemServlet = new AddNewItemServlet(itemService, securityService);
        DeleteItemServlet deleteItemServlet = new DeleteItemServlet(itemService, securityService);
        UpdateItemServlet updateItemServlet = new UpdateItemServlet(itemService, securityService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addFilter(new FilterHolder(new SecurityFilter(securityService)), "/*", EnumSet.of(DispatcherType.REQUEST) );

        context.addServlet(new ServletHolder(showAllItemsRequestServlet), "/");
        context.addServlet(new ServletHolder(addNewItemServlet), "/add");
        context.addServlet(new ServletHolder(deleteItemServlet), "/delete");
        context.addServlet(new ServletHolder(updateItemServlet), "/update");
        context.addServlet(new ServletHolder(new LoginItemServlet(userTokens, securityService)), "/login");
        context.addServlet(new ServletHolder(new SignUpItemServlet(userTokens, securityService, userService)), "/register");
        context.addServlet(new ServletHolder(new LogoutServlet(securityService)),"/logout" );





        Server server = new Server(8080);
        server.setHandler(context);

        server.start();

    }
}
