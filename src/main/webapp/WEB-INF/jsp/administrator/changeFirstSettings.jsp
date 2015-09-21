<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	  <!-- Bootstrap Core CSS -->
	<link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">
		
	<!-- Stylesheet -->
	<link href="<c:url value='/css/stylesheet.css'/>" rel="stylesheet">
	
	<!-- jQuery -->
	<script src="js/jquery.js"></script>
	
	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>
</head>
<body>
<section class="layout-gradient">
	<div class="container">
		<div class="row">
			<div class="col-md-6 layout-gradient-col">
				<img src="<c:url value='/img/Urutau.png'/>"  width="70px">
				<h2 align="center"> Caro administrador, <small>foi detectado que esse é o primeiro acesso ao sistema,
					 portanto é necessário configurar algumas coisas...</small></h2>
				<br/>
			</div>
			<div class="col-md-6 layout-gradient-col">
				<form action="changeFirstSettings" method="POST" class="form-input-user" >
					<input name="user.email" type="email" placeholder="Email: example@email.com" required>
					<br/>
					<input name="user.login" type="text" placeholder="Login: (a..z + 1..9)" required/>
					<br/>
					<input name="user.password" type="password" placeholder="Password: minimum 6 length" required/>
					<br/>
					<input name="user.name" type="text" placeholder="Name: Jhon"/>
					<br/>
					<input name="user.lastName" type="text" placeholder="Lastname: Pereira"/>
					<button class="btn btn-primary btn-block" type="submit">Próxima página (1/2)</button>
				</form>
			</div>
		</div>
	</div>
</section>
</body>
</html>