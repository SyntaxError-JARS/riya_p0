package com.revature.banking.util.logging.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.banking.daos.AccountDao;
import com.revature.banking.daos.UsersDao;
import com.revature.banking.services.AccountServices;
import com.revature.banking.services.UserServices;
import com.revature.banking.util.logging.web.servlets.AccountServlet;
import com.revature.banking.util.logging.web.servlets.AuthServlets;
import com.revature.banking.util.logging.web.servlets.UserServlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ObjectMapper mapper = new ObjectMapper();
        UsersDao usersDao = new UsersDao();
        UserServices userServices = new UserServices(usersDao);


        AccountDao accountDao = new AccountDao();
        AccountServices accountServices = new AccountServices(accountDao);


        AuthServlets authServlets = new AuthServlets(userServices, mapper);
        UserServlets userServlets = new UserServlets(userServices, mapper);
        AccountServlet accountServlet = new AccountServlet(accountServices, mapper);

        ServletContext context = sce.getServletContext();
        context.addServlet("AuthServlet", authServlets).addMapping("/auth");
        context.addServlet("UserServlets", userServlets).addMapping("/users/*");
        context.addServlet("AccountServlet", accountServlet).addMapping("/account/*");



    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
