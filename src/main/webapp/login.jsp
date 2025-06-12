<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<style>

body { background-color: powderblue; }
#wrapper{
	text-align:center;
}

</style>
<body>
<div id="wrapper">
<h2 style="color:green;">Banking System</h2>

<form action="login" method="POST">

	UserName: <input type="text" id="username" name="username" required/>
	<br/></br/>
	Password: <input type="password" id="password" name="password" required/>
	<br/></br/>
	<input type="submit" value="login"/>

</form>
</div>

</body>
</html>