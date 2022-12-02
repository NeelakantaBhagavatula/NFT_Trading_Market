<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Registration</title>
</head>
<body>
	<h2>User Registration. Enter details to register</h2>

<form action="registerUser" method="post">
<pre>
First Name : <input type="text" name="firstName" />
Last Name : <input type="text" name="lastName" />
Email : <input type="text" name="email" />
Nickname : <input type="text" name="nickName" />
Password : <input type="password" name="password" />
Confirm Password : <input type="password" name="confirmPassword" />
<input type="submit" value="Register" />
${msg}
</pre>
</form>
</body>
</html>