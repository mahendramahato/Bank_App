package co.bank.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet("/depo")
public class DepositWithdrawServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userAccountId = (Integer) session.getAttribute("userAccountId");
        if (userAccountId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // for deposit and withdraw
        String message = "";
        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement updateStmt = null;
        PreparedStatement insertTxn = null;
        ResultSet rs = null;

        String type = request.getParameter("transactionType");
        String amountStr = request.getParameter("amount");
        double amount = 0;

        if (amountStr != null && !amountStr.trim().isEmpty()) {
            amount = Double.parseDouble(amountStr.trim());
        } else {
            redirectWithMessage(response, "Amount is required.");
            return;
        }

        String chequeNo = request.getParameter("chequeNo");
        String description = request.getParameter("description");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/account", "root", "root");
            conn.setAutoCommit(false);

            // Get balances
            stmt = conn.prepareStatement("SELECT balance FROM accounts WHERE account_id = ?");
            updateStmt = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE account_id = ?");
            insertTxn = conn.prepareStatement("INSERT INTO transactions (account_id, type, amount, cheque_no, description, date) VALUES (?, ?, ?, ?, ?, NOW())");

            insertTxn.setInt(1, userAccountId);
            insertTxn.setString(2, type);
            insertTxn.setDouble(3, amount);
            insertTxn.setString(4, chequeNo != null ? chequeNo : null);
            insertTxn.setString(5, description != null ? description : null);
            insertTxn.executeUpdate();

            stmt.setInt(1, userAccountId);
            rs = stmt.executeQuery();


            if (!rs.next()) {
                message = "Account not found.";
                conn.rollback();
            } else {
                double currentBalance = rs.getDouble("balance");

                if ("deposit".equals(type)) {
                    currentBalance += amount;
                    updateStmt.setDouble(1, currentBalance);
                    updateStmt.setInt(2, userAccountId);
                    updateStmt.executeUpdate();
                    message = "Deposited $" + amount;
                } else if ("withdraw".equals(type)) {
                    if (currentBalance < amount) {
                        message = "Insufficient funds.";
                        conn.rollback();
                        redirectWithMessage(response, message);
                        return;
                    }
                    currentBalance -= amount;
                    updateStmt.setDouble(1, currentBalance);
                    updateStmt.setInt(2, userAccountId);
                    updateStmt.executeUpdate();
                    message = "Withdrew $" + amount;
                } else {
                    message = "Invalid transaction type.";
                    conn.rollback();
                    redirectWithMessage(response, message);
                    return;
                }

                conn.commit();
            }

            conn.commit();
            conn.close();

        } catch (Exception e) {
            request.setAttribute("message", "Error: " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (SQLException ignore) {}
        } finally{
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
            try { if (stmt != null) stmt.close(); } catch (Exception ignore) {}
            try { if (conn != null) conn.close(); } catch (Exception ignore) {}
        }

        RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
        rd.forward(request, response);
    }

    private void redirectWithMessage(HttpServletResponse response, String message) throws IOException{
        response.sendRedirect("display.jsp?message=" + java.net.URLEncoder.encode(message, "UTF-8"));
    }
}
