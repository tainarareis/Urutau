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
	<link rel="stylesheet" href='<c:url value="/css/stylesheet.css"/>'>

	<!-- Latest compiled and minified JavaScript -->
	<script src='<c:url value="/js/bootstrap.js/"/>'></script>
	<script src='<c:url value="/js/jquery-2.1.4.min.js"/>'></script>
</head>
<body>
<section class="container">
	<h1>Urutau</h1>
	<h4>by Modesteam</h4>
	<h3>Bem vindo ao gerenciador de requisitos que é a sua cara </h3>
</section>

 <div class="row">
	<div class="col-sm-4">
		<form action="register" class="form-signin" method="POST">
			<h2 class="form-signin-heading">Sign in</h2>
			
			<label for="inputEmail" class="sr-only">Email address</label>
			<input name="user.email" type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
			
			<label for="inputLogin" class="sr-only">Login</label>
			<input name="user.login" id="inputLogin" class="form-control" placeholder="Login" required>
			
			<label for="inputPassword" class="sr-only">Password</label>
			<input name="user.password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
			
			<label for="inputName" class="sr-only">Name</label>
			<input name="user.name" id="inputName" class="form-control" placeholder="Name" required>
			
			<label for="inputLastName" class="sr-only">Last name</label>
			<input name="user.lastName" id="inputLastName" class="form-control" placeholder="Last name" required>
			
			<div class="checkbox">
			  
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
		</form>
	</div>
	
	<div class="row">
	<div class="col-sm-4">
		<form action="login" class="form-signin" method="POST">
			<h2 class="form-signin-heading">Login</h2>
			
			<label for="inputEmail" class="sr-only">Email address</label>
			<input name="user.email" type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
			
			<label for="inputLogin" class="sr-only">Login</label>
			<input name="user.login" id="inputLogin" class="form-control" placeholder="Login" required>
			
			<label for="inputPassword" class="sr-only">Password</label>
			<input name="user.password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
			
			
			<div class="checkbox">
			  
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
		</form>
	</div>
	
	     
</div> 
</body>
</html>