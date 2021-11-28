package com.lysenko.payments.servlets.user;

import com.lysenko.payments.model.dao.AccountDao;
import com.lysenko.payments.model.entity.account.Account;
import com.lysenko.payments.model.entity.user.User;
import com.lysenko.payments.servlets.payments.CreatePaymentCommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {
    private final Logger log = Logger.getLogger(CreatePaymentCommand.class);

    private AccountDao accountDao = new AccountDao();

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("try to get sesion");
        HttpSession session = req.getSession();
        log.debug("try to get parameter user");
        User user = (User) session.getAttribute("user");
        log.debug("try to get parameter page");
        String pageParam = req.getParameter("page");
        log.debug("try to get parameter sortBy");
        String sortBy = req.getParameter("sortBy");
        int page = 1;
        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }
        log.debug("try to get accounts");
        List<Account> accounts = accountDao.getAllUserAccounts(user.getUserId(), page, sortBy);
        log.debug("try to get account counts of DB table ");
        final int accountsCount = accountDao.getAccountsCount(user.getUserId());
        int numberOfPages = accountsCount / AccountDao.ACCOUNT_GET_PAGE;
        if (accountsCount % AccountDao.ACCOUNT_GET_PAGE != 0) {
            numberOfPages++;
        }
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("accounts", accounts);
        req.getRequestDispatcher("/user.jsp").forward(req, resp);
    }
}
