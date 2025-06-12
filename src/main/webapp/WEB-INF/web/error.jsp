<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>

<style>

body { background-color: powderblue; }
#wrapper{
	text-align:center;
}

</style>

<body>

<div id="wrapper">
	<p>Error in login!!!</p>
	<br>
	<p> username, <%= request.getAttribute("user") %></p>
	<p> password, <%= request.getAttribute("pwd") %></p>
	<p>Please, Try Again!</p>
	<br><br>
	<a href="<c:url value="/index.jsp" />">Back</a>


</div>



</body>
</html>