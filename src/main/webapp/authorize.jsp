<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
<style>

#wrapper{
	text-align: center;
}

body{
	background-color: powderblue;
}

</style>

<body>

<h2 style="text-align:center; color:green;">Authorize Credit Card Transaction.</h2>

<div id="wrapper">

    <form action="authorize" method="post">
        Card Holder Name: <input type="text" name="cardHolder" required><br><br>
        Card Number: <input type="text" name="cardNumber" required><br><br>
        CCV: <input type="text" name="ccv" required><br><br>
        Transaction Amount: <input type="number" name="amount" required><br><br>
        <input type="submit" value="Authorize">
    </form>

    <% String message = (String) request.getAttribute("message");
       if (message != null) { %>
       <h3><%= message %></h3>
    <% } %>

</div>

</body>
</html>