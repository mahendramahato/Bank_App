<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>

<%
    Integer userAccountId = (Integer) session.getAttribute("userAccountId");
    String accountHolder = (String) session.getAttribute("accountHolder");

    if (userAccountId == null) {
        response.sendRedirect("login.jsp"); // simulate login if needed
        return;
    }
%>

<html>
<head><title>Transaction</title></head>
<body>
    <h2>Money Transfer</h2>
    <h3>Welcome, <%= accountHolder %> (Account ID: <%= userAccountId %>)</h3>

    <form action="transfer" method="post">
        Target Account Number: <input type="text" name="targetAccount" required><br>
        Amount: <input type="number" step="0.01" name="amount" required><br>
           <br><br>
        <input type="submit" name="action" value="Debit From">
        <input type="submit" name="action" value="Credit To">
    </form>

    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
        <p style="color:green;"><%= message %></p>
    <%
        }
    %>
    <br><br>
    <br><br>
    <a href="<c:url value="/main.jsp" />">Back</a>
</body>
</html>

