package com.lysenko.payments.servlet.admin;


import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/registration_from_admin")
public class RegistrationFromAdminServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(RegistrationFromAdminServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("try to get parameters");
        String error = req.getParameter("error");
        String success = req.getParameter("success");
        log.debug("try to set parameters");
        req.setAttribute("error", error);
        req.setAttribute("success", success);
        req.getRequestDispatcher("registration_admin.jsp").forward(req, resp);
    }
}
