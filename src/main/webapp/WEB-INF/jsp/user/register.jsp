<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>Registro</title>
		<meta name="generator" content="Bootply" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.min.css" rel="stylesheet">
		<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<link href="css/styles.css" rel="stylesheet">
	</head>
</head>
<body>
	<form action="register" method="POST">
		Nome: <input name="user.name" type="text"/>
		<br/>
		<br/>
		<br/>
		Login: <input name="user.login" type="text"/>
		<br/>
		<br/>
		<br/>
		Password: <input name="user.password" type="password"/>
		<br/>
		<br/>
		<br/>
		Confirm Password: <input name="user.passwordVerify" type="password"/>
		<br/>
		<br/>
		<br/>
		Email: <input name="user.email" type="text"/>
		<br/>
		<br/>
		<p>${mensagem}  
		<input type="submit"/>
	</form>
	</body>
</html>