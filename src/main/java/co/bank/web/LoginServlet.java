package co.bank.web;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/account";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // (Fake check)
//        if ("admin".equals(username) && "secret".equals(password)) {
//            RequestDispatcher rd = req.getRequestDispatcher("/main.jsp");
//            rd.forward(req, resp); // success
//        } else {
//            req.setAttribute("user", username);
//            req.setAttribute("pwd", password);
//            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/web/error.jsp");
//            rd.forward(req, resp);
//        }

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");  // load mysql jdbc driver
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM accounts WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){

                int accountId = rs.getInt("account_id");
                String accountHolder = rs.getString("account_holder");

                //Set account_id in session
                HttpSession session = req.getSession();
                session.setAttribute("accountId", accountId);
                session.setAttribute("accountHolder", accountHolder);

                // redirect
                RequestDispatcher rd = req.getRequestDispatcher("/main.jsp");
                rd.forward(req, resp);
            }else{
                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/web/error.jsp");
                rd.forward(req, resp);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

}
