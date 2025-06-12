<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>

<style>

h2{
	color: green;
}

body { background-color: powderblue; }
#wrapper{
	text-align:center;
}

</style>

<body>

<div id="wrapper">
<h2>Banking System</h2>

<a href="<c:url value='/account.jsp' />">Create Account</a>
<br/>
<a href="<c:url value='/transactions.jsp' />">Transaction</a>
<br/>
<a href="display">Display</a>
<br/>
<a href="<c:url value='/authorize.jsp' />">Authorize Credit Card</a>
<br/>
<a href="<c:url value='/deposit.jsp' />">Deposit / Withdraw </a>

</div>

</body>
</html>