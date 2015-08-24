<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
	<h1>Welcome! Login</h1>
	<form action="login" method="POST">
		Login : <input name="user.login" placeholder="Example" type="text">
		Password : <input name="user.password" type="password">
		<input type="submit" value="Login">
	</form>
</body>
</html>