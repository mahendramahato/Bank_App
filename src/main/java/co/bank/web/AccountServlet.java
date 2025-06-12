package co.bank.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AccountServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/account";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String name = request.getParameter("name");
        String dobstr = request.getParameter("dob");
        Date dob = Date.valueOf(dobstr);

        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String accountType = request.getParameter("accountType");

        // JDBC logic
        try {
            // Load driver (optional for newer JDBC)
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "INSERT INTO account (name, dob, address, email, account_type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDate(2, dob);
            ps.setString(3, address);
            ps.setString(4, email);
            ps.setString(5, accountType);

            int rows = ps.executeUpdate();

            conn.close();

            // Success → forward to JSP
            request.setAttribute("name", name);
            request.setAttribute("accountType", accountType);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/web/account-success.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
        }

    }

}
