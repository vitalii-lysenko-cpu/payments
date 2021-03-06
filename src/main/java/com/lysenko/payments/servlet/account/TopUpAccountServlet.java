package com.lysenko.payments.servlet.account;

import com.lysenko.payments.model.dao.AccountDao;
import com.lysenko.payments.model.entity.account.MarkChangeBalance;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/top_up"})
public class TopUpAccountServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(TopUpAccountServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.debug("try to get accountId from param.");
        int accountId = Integer.parseInt(req.getParameter("accountId"));
        log.debug("accountId :" + accountId);
        log.debug("try to get total from param");
        String tot = req.getParameter("total");
        log.debug("total :" + tot);
        if (!tot.isEmpty()) {
            double total = Double.parseDouble(tot);
            if(total>=0) {
                AccountDao accountDao = new AccountDao();
                accountDao.changeBalance(total, accountId, MarkChangeBalance.PLUS);
                log.debug("total is not empty, change balance");
                resp.sendRedirect("/account?id=" + accountId);
            }else {
                resp.sendRedirect("/account?error=theValueMustNotBeNegative&id=" + accountId);
            }
        } else {
            log.debug("total is empty, redirect to getHeader \"referer\"");
            resp.sendRedirect(req.getHeader("referer"));
        }
    }
}
