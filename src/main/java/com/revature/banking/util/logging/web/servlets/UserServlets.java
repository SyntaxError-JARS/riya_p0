package com.revature.banking.util.logging.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.banking.daos.UsersDao;
import com.revature.banking.exceptions.ResourcePersistanceException;
import com.revature.banking.models.User;
import com.revature.banking.services.UserServices;
import com.revature.banking.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.revature.banking.util.logging.web.servlets.Authable.checkAuth;

public class UserServlets extends HttpServlet implements Authable {
    private final Logger logger = Logger.getLogger();

    private final UserServices userServices = new UserServices(new UsersDao());
    private final ObjectMapper mapper = new ObjectMapper();

    public UserServlets(UserServices userServices, ObjectMapper mapper) {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!checkAuth(req, resp)) return;// check to see if user logged in

        if (req.getParameter("email") != null) {//first is to single user
            User user = new User();

            try {
                user = userServices.readByEmail(req.getParameter("email"));// EVERY PARAMETER RETURN FROM A URL IS A STRING
            } catch (ResourcePersistanceException e) {
                logger.warn(e.getMessage());
                resp.setStatus(404);
                resp.getWriter().write(e.getMessage());
            }

            String payload = mapper.writeValueAsString(user);
            resp.getWriter().write(payload);
            return;
        }
        try {// for multiuser
            User[] users = userServices.readAll();

            String payload = mapper.writeValueAsString(users);


            resp.getWriter().write(payload);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("exception");
        }


    }




    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
           User user = mapper.readValue(req.getInputStream(),User.class);
            User user1 = userServices.create(user);
            String payload = mapper.writeValueAsString(user1);
            resp.getWriter().write((payload));
            resp.setStatus(201);


        } catch (Exception e) {
            e.printStackTrace();

            resp.getWriter().write("exception");
        }


    }

    @Override
    protected void doDelete (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!checkAuth(req, resp)) return;// check to see if user logged in
        // String pathInfo = req.getPathInfo();
        //String[] pathParts = pathInfo.split("/");
        //System.out.println(pathParts[0] + pathParts[1]);

        // Handling the query params in the endpoint /users?id=x
        if (req.getParameter("email") == null) {
            resp.getWriter().write("Hey you have the follow email "  + req.getParameter("email"));
            return;
        }


            try {
                userServices.delete(req.getParameter("email"));// EVERY PARAMETER RETURN FROM A URL IS A STRING
            } catch (ResourcePersistanceException e) {
                logger.warn(e.getMessage());
                resp.setStatus(404);
                resp.getWriter().write(e.getMessage());
            }


            resp.getWriter().write("user successfully deleted");
            return;
        }

    }







