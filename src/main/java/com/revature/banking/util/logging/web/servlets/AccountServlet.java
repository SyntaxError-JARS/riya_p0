package com.revature.banking.util.logging.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.banking.daos.AccountDao;
import com.revature.banking.exceptions.ResourcePersistanceException;
import com.revature.banking.models.Account;
import com.revature.banking.services.AccountServices;
import com.revature.banking.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.revature.banking.util.logging.web.servlets.Authable.checkAuth;

//@WebServlet("/accounts")
public class AccountServlet extends HttpServlet implements Authable {
    //private final Logger logger = Logger.getLogger();

    private final AccountServices accountServices;
    private final ObjectMapper mapper;


    public AccountServlet(AccountServices accountServices, ObjectMapper mapper) {
        this.accountServices = accountServices;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!checkAuth(req, resp)) return;
        //String pathInfo = req.getPathInfo();
        // String[] pathParts = pathInfo.split("/");
        // System.out.println(pathParts[0] + pathParts[1]);

        if (req.getParameter("id") != null) {
            Account account = accountServices.readById(req.getParameter("id"));// EVERY PARAMETER RETURN FROM A URL IS A STRING
            String payload = mapper.writeValueAsString(account);
            resp.getWriter().write(payload);
            return;
        }
        Account[] accounts = accountServices.readAll();
        String payload = mapper.writeValueAsString(accounts);
        resp.getWriter().write(payload);


    }


    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!checkAuth(req, resp)) return;
//TODO: Add account auth
        String payload = "";
        if (req.getParameter("id") != null || req.getParameter("value") != null) {
            Account account;
            try {
                accountServices.deposit(req.getParameter("value"), req.getParameter("id")); // EVERY PARAMETER RETURN FROM A URL IS A STRING
                account = accountServices.readById(req.getParameter("id"));

            } catch (ResourcePersistanceException e) {
                resp.setStatus(404);
                return;
            }
            payload = mapper.writeValueAsString(account);
            resp.getWriter().write(payload);
            resp.setStatus(201);
            return;
        }
        resp.getWriter().write("Invalid value or id");

    }


    private boolean validateAccountInput(Account newAccount) {
        if (newAccount == null) return false;
        if (newAccount.getId() == 0) return false;
        if (newAccount.getAccount_type() == null || newAccount.getAccount_type().trim().equals("")) return false;
        return newAccount.getEmail() != null || !newAccount.getEmail().trim().equals("");
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!checkAuth(req, resp)) return;

        if (req.getParameter("id") != null) {
            try {
                accountServices.deleteAccount(req.getParameter("id")); // EVERY PARAMETER RETURN FROM A URL IS A STRING
            } catch (ResourcePersistanceException e) {
                resp.setStatus(404);
                return;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            resp.getWriter().write("Account " +  req.getParameter("id") + " Deleted");
            resp.setStatus(201);
            return;
        }
        resp.getWriter().write("Account " +  req.getParameter("id") + " Not Found");
    }



    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //if(!checkAuth(req, resp)) return;
        Account newAccount = mapper.readValue(req.getInputStream(), Account.class); // from JSON to Java Object (user)
        accountServices.registerAccount(newAccount);

        String payload = mapper.writeValueAsString(newAccount); // Mapping from Java Object (user) to JSON

        resp.getWriter().write(payload);
        resp.setStatus(201);
    }
}




