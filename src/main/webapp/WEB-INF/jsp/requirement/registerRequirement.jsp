<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Urutau - Registrar Requisito</title>

<!-- Bootstrap Core CSS -->
<link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">

<!-- Custom CSS -->
<link href="<c:url value='/css/agency.css'/>" rel="stylesheet">

<!-- Stylesheet -->
<link href="<c:url value='/css/stylesheet.css'/>" rel="stylesheet">
</head>

<body>
	<h1>Registre um requisito!</h1>

	<select id="mySelect" onchange="myFunction()">
		<option value="generico">Genérico
		<option value="historia">História de Usuário
		<option value="casodeUso">Caso de Uso
	</select>

	<script>
		function myFunction() {
			var x = document.getElementById("mySelect").value;
			if (x == "generico") {
				document.getElementById("generico").style.hidden = "visible";
			}

		}
	</script>

	<div id="generico" hidden = "invisible">
		<form action="registerRequirement" method="POST">
			generico : <input name="requirement.title" placeholder="Título"
				type="text"> Descrição : <input
				name="requirement.description" placeholder="Descrição" type="text">
			<input type="submit" value="Login">
		</form>
	</div>

	<div id="historia">
		<form action="registerRequirement" method="POST">
			historia : <input name="requirement.title" placeholder="Título"
				type="text"> Descrição : <input
				name="requirement.description" placeholder="Descrição" type="text">
			<input type="submit" value="Login">
		</form>
	</div>
</body>
</html>