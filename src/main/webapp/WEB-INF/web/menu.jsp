<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%!
	String name;
	String pass;
%>

<html>

<body>

<%

	name = request.getParameter("username");
	pass = request.getParameter("pass");	
	
	if(name.equals("admin") && pass.equals("1234")){
		response.sendRedirect("main.jsp");
	}else{
		response.sendRedirect("error.jsp");		
	}
	
%>

</body>
</html>