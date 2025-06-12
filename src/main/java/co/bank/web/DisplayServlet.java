package co.bank.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

@WebServlet("/display")
public class DisplayServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/account";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{

        HttpSession session = request.getSession(false);
        Integer accountId = (Integer) session.getAttribute("accountId");

        if (accountId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        List<Map<String, String>> transactions = new ArrayList<>();

        String sql = "SELECT * FROM transactions WHERE account_id = ?";
        boolean useDateFilter = false;

        if (startDate != null && endDate != null && !startDate.isEmpty() && !endDate.isEmpty()) {
            sql += " AND DATE(date) BETWEEN ? AND ?";
            useDateFilter = true;
        }

        try (

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);

            if (useDateFilter) {
                stmt.setString(2, startDate);
                stmt.setString(3, endDate);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> transaction = new HashMap<>();
                    transaction.put("type", rs.getString("type"));
                    transaction.put("amount", rs.getString("amount"));
                    transaction.put("chequeNo", rs.getString("cheque_no"));
                    transaction.put("description", rs.getString("description"));
                    transaction.put("date", rs.getString("date"));
                    transactions.add(transaction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load transactions: " + e.getMessage());
        }

        System.out.println("Session accountId: " + accountId);
        System.out.println("Session ID: " + session.getId());
        System.out.println("Account ID from session: " + accountId);
        System.out.println("Transactions found: " + transactions.size());

        request.setAttribute("transactions", transactions);
        RequestDispatcher dispatcher = request.getRequestDispatcher("display.jsp");
        dispatcher.forward(request, response);



    }

}
