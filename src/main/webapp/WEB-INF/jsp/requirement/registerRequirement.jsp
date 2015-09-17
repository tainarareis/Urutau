<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
	<h1>Registre um requisito!</h1>
	<form action="registerRequirement" method="POST">
		Titulo : <input name="requirement.title" placeholder="Título" type="text">
		Descrição : <input name="requirement.description" placeholder="Descrição" type="text">
		<input type="submit" value="Login">
	</form>
</body>
</html>