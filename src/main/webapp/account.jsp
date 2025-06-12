<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
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
	
	<h2>Account Creation Screen</h2>
	
	<form action="createAccount" method="post">
		
		Name: <input type="text" name="name" />
		<br/><br/>
		DOB: <input type="date" name="dob" />
		<br/><br/>
		Address: 
		<textarea type="text" name="address"></textarea>
		<br/><br/>
		Email ID: <input type="text" name="email" />
		<br/><br/>
		Type Of Account: 
		<select id="accounts" name="accountType">
			<option value="saving" name="saving">SB Account</option>
			<option value="checking" name="checking">Checking Account</option>
		</select>
		<br/><br/>
		<input type="submit" value="Create Account" />
	
	</form>
		
	
</div>

</body>
</html>