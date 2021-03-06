package com.lysenko.payments.servlet.account;

import com.lysenko.payments.model.dao.AccountDao;
import com.lysenko.payments.model.dao.CardDao;
import com.lysenko.payments.model.dao.PaymentDao;
import com.lysenko.payments.model.entity.Card;
import com.lysenko.payments.model.entity.payment.Payment;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/account"})
public class AccountServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(AccountServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (Integer.parseInt(id) <= 0) {
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
        String pageParam = req.getParameter("page");
        String sortBy = req.getParameter("sortBy");
        String error = req.getParameter("error");
        if (sortBy == null) {
            sortBy = "id";
        }
        String sortOrder = req.getParameter("sortOrder");
        if (sortOrder == null) {
            sortOrder = "ASC";
        }
        req.setAttribute("sortOrder", sortOrder);
        req.setAttribute("sortBy", sortBy);

        int page = 1;
        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
            if (page <= 0) {
                page = 1;
            }
        }
        CardDao cardDao = new CardDao();
        PaymentDao paymentDao = new PaymentDao();
        AccountDao accountDao = new AccountDao();
        log.debug("try to get cards");
        List<Card> cards = cardDao.getGetAccountCard(id);
        log.debug("cards :" + cards);
        req.setAttribute("cards", cards);

        log.debug("try to get payments for account");
        List<Payment> payments = paymentDao.getPaymentForAccount(id, page, sortOrder, sortBy);
        log.debug("payments :" + payments);
        req.setAttribute("payments", payments);
        final int total = paymentDao.getPaymentsCount(id);
        int numberOfPages = total / PaymentDao.ACCOUNTS_PER_PAGE;
        if (total % PaymentDao.ACCOUNTS_PER_PAGE != 0) {
            numberOfPages++;
        }
        log.debug("try to get balance by id");
        double balance = accountDao.getAccountBalance(id);
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("balance", balance);
        req.setAttribute("page", page);
        req.setAttribute("error",error);
        log.debug("try set attribute numberOfPage :" + numberOfPages + "balance" + balance);
        req.getRequestDispatcher("/account.jsp").forward(req, resp);
    }
}
