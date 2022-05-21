package com.revature.banking.util.logging.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.banking.exceptions.InvalidRequestException;
import com.revature.banking.models.User;
import com.revature.banking.services.UserServices;
import com.revature.banking.util.logging.web.dto.LoginCreds;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


//@WebServlet("/auth") ///???
public class AuthServlets extends HttpServlet {


    private final UserServices userServices;
    private final ObjectMapper mapper;

    public AuthServlets(UserServices userServices, ObjectMapper mapper) {
        this.userServices = userServices;
        this.mapper = mapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
           // User requser = mapper.readValue(req.getInputStream(), User.class);
            LoginCreds loginCreds = mapper.readValue(req.getInputStream(), LoginCreds.class);

            User authUser = userServices.authenticateUser(loginCreds.getEmail(), loginCreds.getUserpassword());

            HttpSession httpSession = req.getSession(true);
            httpSession.setAttribute("authUser", authUser);
            resp.getWriter().write("You have successfully logged in!");

            resp.setStatus(200);

        } catch (InvalidRequestException e){
            resp.setStatus(404);
            resp.getWriter().write(e.getMessage());
        } catch (Exception e){
            resp.setStatus(500);
            resp.getWriter().write(e.getMessage());
        }


    }


}
