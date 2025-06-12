package co.bank.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/authorize")
public class AuthorizeServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/account";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{

        String cardHolder = request.getParameter("cardHolder");
        String cardNumber = request.getParameter("cardNumber");
        String ccv = request.getParameter("ccv");
        double amount = Double.parseDouble(request.getParameter("amount"));

        String message;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                String sql = "SELECT * FROM credit_card WHERE card_holder=? AND card_number=? AND ccv=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, cardHolder);
                stmt.setString(2, cardNumber);
                stmt.setString(3, ccv);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    if (amount < 100000) {
                        message = "Transaction Approved!";
                    } else {
                        message = "Transaction Rejected: Amount exceeds ₹1,00,000 limit.";
                    }
                } else {
                    message = "Transaction Rejected: Card not found or invalid.";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Error occurred while processing the transaction.";
        }

        request.setAttribute("message", message);
        RequestDispatcher rd = request.getRequestDispatcher("authorize.jsp");
        rd.forward(request, response);


    }

}
