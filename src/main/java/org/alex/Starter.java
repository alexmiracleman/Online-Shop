package org.alex;


import jakarta.servlet.DispatcherType;
import org.alex.dao.jdbc.ConnectionFactory;
import org.alex.dao.jdbc.JdbcItemDao;
import org.alex.dao.jdbc.JdbcSaltDao;
import org.alex.dao.jdbc.JdbcUserDao;
import org.alex.service.ItemService;
import org.alex.service.SaltService;
import org.alex.service.UserService;
import org.alex.util.CachedPropertiesReader;
import org.alex.web.filter.SecurityFilter;
import org.alex.web.servlet.*;
import org.alex.service.SecurityService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.*;

public class Starter {

    private static final String PROPERTIES = "application.properties";

    public static void main(String[] args) throws Exception {
        //properties
        CachedPropertiesReader cachedPropertiesReader = new CachedPropertiesReader(PROPERTIES);
        Properties properties = cachedPropertiesReader.getCachedProperties();
        //dao
        ConnectionFactory connectionFactory = new ConnectionFactory(properties);
        JdbcItemDao jdbcItemDao = new JdbcItemDao(connectionFactory);
        JdbcUserDao jdbcUserDao = new JdbcUserDao(connectionFactory);
        JdbcSaltDao jdbcSaltDao = new JdbcSaltDao(connectionFactory);


        //service
        ItemService itemService = new ItemService(jdbcItemDao);
        UserService userService = new UserService(jdbcUserDao);
        SaltService saltService = new SaltService(jdbcSaltDao);
        List<String> userTokens = Collections.synchronizedList(new ArrayList<>());
        SecurityService securityService = new SecurityService(userTokens, userService, saltService);
        //servlet
        ShowAllItemsRequestServlet showAllItemsRequestServlet = new ShowAllItemsRequestServlet(itemService);
        AddNewItemServlet addNewItemServlet = new AddNewItemServlet(itemService);
        DeleteItemServlet deleteItemServlet = new DeleteItemServlet(itemService);
        UpdateItemServlet updateItemServlet = new UpdateItemServlet(itemService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addFilter(new FilterHolder(new SecurityFilter(securityService)), "/*", EnumSet.of(DispatcherType.REQUEST) );

        context.addServlet(new ServletHolder(showAllItemsRequestServlet), "/items");
        context.addServlet(new ServletHolder(addNewItemServlet), "/items/add");
        context.addServlet(new ServletHolder(deleteItemServlet), "/items/delete");
        context.addServlet(new ServletHolder(updateItemServlet), "/items/update");
        context.addServlet(new ServletHolder(new LoginItemServlet(userTokens, securityService)), "/login");
        context.addServlet(new ServletHolder(new SignUpItemServlet(securityService, userService,saltService)), "/register");
        context.addServlet(new ServletHolder(new LogoutServlet(securityService)),"/logout" );

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();

    }
}
