package org.alex;

import jakarta.servlet.DispatcherType;
import org.alex.context.ApplicationContext;
import org.alex.context.ClassPathApplicationContext;
import org.alex.web.filter.SecurityFilter;
import org.alex.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.EnumSet;

public class Starter {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathApplicationContext("/context/context.xml");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addFilter(new FilterHolder(applicationContext.getBean(SecurityFilter.class)), "/*", EnumSet.of(DispatcherType.REQUEST));

        context.addServlet(new ServletHolder(applicationContext.getBean(ShowAllItemsRequestServlet.class)), "/admin/items");
        context.addServlet(new ServletHolder(applicationContext.getBean(AddNewItemServlet.class)), "/admin/items/add");
        context.addServlet(new ServletHolder(applicationContext.getBean(DeleteItemServlet.class)), "/admin/items/delete");
        context.addServlet(new ServletHolder(applicationContext.getBean(UpdateItemServlet.class)), "/admin/items/update");
        context.addServlet(new ServletHolder(applicationContext.getBean(ShowAllUserItemsRequestServlet.class)), "/user/items");
        context.addServlet(new ServletHolder(applicationContext.getBean(CartServlet.class)), "/user/cart");
        context.addServlet(new ServletHolder(applicationContext.getBean(AddItemToCartServlet.class)), "/user/cart/add");
        context.addServlet(new ServletHolder(applicationContext.getBean(RemoveItemFromCartServlet.class)), "/user/cart/remove");
        context.addServlet(new ServletHolder(applicationContext.getBean(LoginServlet.class)), "/login");
        context.addServlet(new ServletHolder(applicationContext.getBean(SignUpServlet.class)), "/register");
        context.addServlet(new ServletHolder(applicationContext.getBean(LogoutServlet.class)), "/logout");

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
    }
}
