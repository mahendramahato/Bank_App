
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>


<%
    Integer accountId = (Integer) session.getAttribute("accountId");
    String accountHolder = (String) session.getAttribute("accountHolder");
    List<Map<String, String>> transactions = (List<Map<String, String>>) request.getAttribute("transactions");

    if (accountId == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transaction History</title>
    <style>
        body { font-family: Arial; background: #eef2f5; padding: 30px; }
        .container { background: white; max-width: 800px; margin: auto; padding: 30px; border-radius: 10px; box-shadow: 0 0 12px rgba(0,0,0,0.1); }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 10px; border-bottom: 1px solid #ccc; text-align: center; }
        th { background-color: #007bff; color: white; }
        h2 { text-align: center; }
    </style>
</head>
<body>
<div class="container">

    <div>
        <form method="get" action="display">
            Start Date: <input type="date" name="startDate" value="<%= request.getParameter("startDate") != null ? request.getParameter("startDate") : "" %>" required>
            End Date: <input type="date" name="endDate" value="<%= request.getParameter("endDate") != null ? request.getParameter("endDate") : "" %>" required>
            <button type="submit">Display</button>
        </form>
    </div>

    <h2>Transaction History for <%= accountHolder %> (<%= accountId %>)</h2>

    <% if (transactions == null || transactions.isEmpty()) { %>
        <p>No transactions found.</p>
    <% } else { %>
        <table>
            <tr>
                <th>Type</th>
                <th>Amount</th>
                <th>Cheque No</th>
                <th>Description</th>
                <th>Date</th>
            </tr>
            <% for (Map<String, String> txn : transactions) { %>
                <tr>
                    <td><%= txn.get("type") %></td>
                    <td><%= txn.get("amount") %></td>
                    <td><%= txn.get("chequeNo") != null ? txn.get("chequeNo") : "-" %></td>
                    <td><%= txn.get("description") != null ? txn.get("description") : "-" %></td>
                    <td><%= txn.get("date") %></td>
                </tr>
            <% } %>
        </table>
    <% } %>

    <br><br>
    <br><br>
    <a href="<c:url value="/main.jsp" />">Back</a>
</div>
</body>
</html>
