package com.revature.banking.util.logging.web.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface Authable {

    static boolean checkAuth(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession httpSession = req.getSession();
       // resp.getWriter().write((httpSession.getAttribute("authUser")).toString());
        if (httpSession.getAttribute("authUser") == null) {
            resp.getWriter().write("Unauthorize request - not logged in as register user");
            resp.setStatus(401); //unauthorized
            return false;

        }
        return true;

    }
}