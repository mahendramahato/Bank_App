package co.bank.web;
import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/transfer")
public class TransactionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer userAccountId = (Integer) session.getAttribute("userAccountId");
        if (userAccountId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        // for debit and credit transaction
        String targetAccountStr = request.getParameter("targetAccount");
        String amountStr = request.getParameter("amount");
        String action = request.getParameter("action");

        try {
            int targetAccount = Integer.parseInt(targetAccountStr);
            double amount = Double.parseDouble(amountStr);

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/account", "root", "root");
            conn.setAutoCommit(false);

            // Get balances
            PreparedStatement balanceStmt = conn.prepareStatement("SELECT balance FROM accounts WHERE account_id = ?");
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE account_id = ?");
            PreparedStatement insertTxn = conn.prepareStatement("INSERT INTO transactions (account_id, type, amount, date) VALUES (?, ?, ?, NOW())");

            // for debit and credit from
            if ("Debit From".equals(action)) {
                // 1. Get target balance
                balanceStmt.setInt(1, targetAccount);
                ResultSet rs1 = balanceStmt.executeQuery();
                if (!rs1.next()) throw new Exception("Target account does not exist.");
                double targetBalance = rs1.getDouble("balance");
                if (targetBalance < amount) throw new Exception("Insufficient balance in target account.");

                // 2. Update balances
                updateStmt.setDouble(1, targetBalance - amount);
                updateStmt.setInt(2, targetAccount);
                updateStmt.executeUpdate();

                balanceStmt.setInt(1, userAccountId);
                ResultSet rs2 = balanceStmt.executeQuery();
                rs2.next();
                double userBalance = rs2.getDouble("balance");

                updateStmt.setDouble(1, userBalance + amount);
                updateStmt.setInt(2, userAccountId);
                updateStmt.executeUpdate();

                // 3. Record transactions
                insertTxn.setInt(1, targetAccount);
                insertTxn.setString(2, "debit");
                insertTxn.setDouble(3, amount);
                insertTxn.executeUpdate();

                insertTxn.setInt(1, userAccountId);
                insertTxn.setString(2, "credit");
                insertTxn.setDouble(3, amount);
                insertTxn.executeUpdate();

                request.setAttribute("message", "Debited from " + targetAccount + " to your account successfully.");
            }

            else if ("Credit To".equals(action)) {
                // 1. Get user balance
                balanceStmt.setInt(1, userAccountId);
                ResultSet rs1 = balanceStmt.executeQuery();
                if (!rs1.next()) throw new Exception("Your account does not exist.");
                double userBalance = rs1.getDouble("balance");
                if (userBalance < amount) throw new Exception("Insufficient balance in your account.");

                // 2. Update balances
                updateStmt.setDouble(1, userBalance - amount);
                updateStmt.setInt(2, userAccountId);
                updateStmt.executeUpdate();

                balanceStmt.setInt(1, targetAccount);
                ResultSet rs2 = balanceStmt.executeQuery();
                if (!rs2.next()) throw new Exception("Target account does not exist.");
                double targetBalance = rs2.getDouble("balance");

                updateStmt.setDouble(1, targetBalance + amount);
                updateStmt.setInt(2, targetAccount);
                updateStmt.executeUpdate();

                // 3. Record transactions
                insertTxn.setInt(1, userAccountId);
                insertTxn.setString(2, "debit");
                insertTxn.setDouble(3, amount);
                insertTxn.executeUpdate();

                insertTxn.setInt(1, targetAccount);
                insertTxn.setString(2, "credit");
                insertTxn.setDouble(3, amount);
                insertTxn.executeUpdate();

                request.setAttribute("message", "Credited from your account to " + targetAccount + " successfully.");
            }

            conn.commit();
            conn.close();

        } catch (Exception e) {
            request.setAttribute("message", "Error: " + e.getMessage());
        }

        RequestDispatcher rd = request.getRequestDispatcher("transactions.jsp");
        rd.forward(request, response);
    }
}
