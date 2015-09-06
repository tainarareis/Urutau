<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>VRaptor Blank Project</title>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href='<c:url value="/css/bootstrap.min.css"/>'>

	<!-- Optional theme -->
	<link rel="stylesheet" href='<c:url value="/css/bootstrap-theme.min.css"/>'>

	<!-- Latest compiled and minified JavaScript -->
	<script src='<c:url value="/js/bootstrap.js/"/>'></script>
	<script src='<c:url value="/js/jquery-2.1.4.min.js"/>'></script>
</head>
<body>
<section class="container">
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
</section>
</body>
</html>