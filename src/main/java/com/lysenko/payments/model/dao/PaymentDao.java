package com.lysenko.payments.model.dao;

import com.lysenko.payments.model.Pool;
import com.lysenko.payments.model.entity.payment.Payment;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.lysenko.payments.model.dao.RowsCounterInTable.getCountBY;
import static com.lysenko.payments.model.entity.payment.PaymentStatus.DONE;
import static com.lysenko.payments.model.entity.payment.PaymentStatus.NEW;

public class PaymentDao {
    public static final int ACCOUNTS_PER_PAGE = 3;
    private static final String ADD_NEW_PAYMENT = "INSERT INTO payment (status, date, amount, account_id) VALUES (?,?,?,?)";
    private static final String GET_PAYMENTS_COUNT = "SELECT COUNT(*) AS numberOfPayments FROM payment WHERE account_id = ?";
    private static final String GET_ACCOUNT_PAYMENT = "SELECT * FROM payment WHERE account_id=? ORDER BY %s %s LIMIT ?,?";
    private static final String GET_INFO_PAYMENT_FOR_PDF = "SELECT * FROM payment WHERE id=?";

    public List<Payment> getPaymentForAccount(String accountId, int page, String sortOrder, String sortBy) {
        return getPaymentForAccount(accountId, page, sortOrder, GET_ACCOUNT_PAYMENT, sortBy);
    }

    public List<Payment> getPaymentForAccount(String accountId, int page, String sortOrder, String query, String sortBy) {
        String sql = String.format(query, sortBy, sortOrder);
        int offset = page * ACCOUNTS_PER_PAGE - ACCOUNTS_PER_PAGE;
        try (Connection connection = Pool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountId);
            statement.setInt(2, offset);
            statement.setInt(3, ACCOUNTS_PER_PAGE);
            ResultSet rs = statement.executeQuery();
            return resultSetToResult(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptyList();
    }


    public int getPaymentsCount(String accountId) {
        return getCountBY(accountId, GET_PAYMENTS_COUNT);
    }

    private List<Payment> resultSetToResult(ResultSet rs) throws SQLException {
        List<Payment> result = new ArrayList<>();
        while (rs.next()) {
            Payment payment = new Payment(
                    rs.getInt("id"),
                    rs.getString("status").equalsIgnoreCase("new") ? NEW : DONE,
                    rs.getDate("date"),
                    rs.getDouble("amount")
            );
            result.add(payment);
        }
        return result;
    }

    public void addNewPayment(double amount, int accountId, Connection connection) {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try (PreparedStatement pr = connection.prepareStatement(ADD_NEW_PAYMENT)) {
            pr.setString(1, "new");
            pr.setDate(2, sqlDate);
            pr.setDouble(3, amount);
            pr.setInt(4, accountId);
            pr.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Payment searchDataForPDF(String id) throws IOException {
        try (Connection connection = Pool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_INFO_PAYMENT_FOR_PDF)) {
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("id"),
                        rs.getString("status").equalsIgnoreCase("new") ? NEW : DONE,
                        rs.getDate("date"),
                        rs.getDouble("amount")
                );
                return payment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




}
