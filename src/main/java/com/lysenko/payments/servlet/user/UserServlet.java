package com.lysenko.payments.servlet.user;

import com.lysenko.payments.model.dao.AccountDao;
import com.lysenko.payments.model.entity.account.Account;
import com.lysenko.payments.model.entity.user.User;
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
    private final Logger log = Logger.getLogger(UserServlet.class);

    private AccountDao accountDao = new AccountDao();

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("try to get session");
        HttpSession session = req.getSession();
        log.debug("try to get parameter user");
        User user = (User) session.getAttribute("user");
        log.debug("try to get parameter page");
        String pageParam = req.getParameter("page");
        log.debug("try to get parameter sortBy");
        String sortBy = req.getParameter("sortBy");
        if (sortBy == null) {
            sortBy = "id";
        }
        String sortOrder = req.getParameter("sortOrder");
        if (sortOrder == null) {
            sortOrder = "ASC";
        }
        int page = 1;
        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
            if(page <= 0){
                page = 1;
            }
        }
        String info = req.getParameter("info");
        req.setAttribute("info", info);
        req.setAttribute("sortBy", sortBy);
        req.setAttribute("sortOrder", sortOrder);
        log.debug("try to get accounts");
        List<Account> accounts = accountDao.getAllUserAccounts(user.getUserId(), page, sortBy, sortOrder);
        log.debug("try to get account counts of DB table ");
        final int accountsCount = accountDao.getAccountsCount(user.getUserId());
        log.debug("accountsCount; " + accountsCount + accounts);
        int numberOfPages = accountsCount / AccountDao.ACCOUNT_GET_PAGE;
        if (accountsCount % AccountDao.ACCOUNT_GET_PAGE != 0) {
            numberOfPages++;
        }
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("accounts", accounts);
        req.setAttribute("page", page);
        req.getRequestDispatcher("/user.jsp").forward(req, resp);
    }
}
