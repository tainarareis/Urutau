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
		<option value="nenhum">Nenhum
		<option value="generico">Genérico
		<option value="historia">História de Usuário
		<option value="casodeUso">Caso de Uso
	</select>

	<script>
		function myFunction() {
			var x = document.getElementById("mySelect").value;
			if (x == "generico") {
				document.getElementById("formGenerico").setAttribute('style','visibility:visible');
				document.getElementById("formHistoria").setAttribute('style','visibility:hidden');
				document.getElementById("formCasoDeUso").setAttribute('style','visibility:hidden');
			} else if (x == "historia"){
				document.getElementById("formHistoria").setAttribute('style','visibility:visible');
				document.getElementById("formGenerico").setAttribute('style','visibility:hidden');
				document.getElementById("formCasoDeUso").setAttribute('style','visibility:hidden');
			} else if (x == "casodeUso"){
				document.getElementById("formCasoDeUso").setAttribute('style','visibility:visible');
				document.getElementById("formGenerico").setAttribute('style','visibility:hidden');
				document.getElementById("formHistoria").setAttribute('style','visibility:hidden');
			} else {
				document.getElementById("formGenerico").setAttribute('style','visibility:hidden');
				document.getElementById("formHistoria").setAttribute('style','visibility:hidden');
				document.getElementById("formCasoDeUso").setAttribute('style','visibility:hidden');
				
			}

		}
	</script>

	<div id="formGenerico" style="visibility:hidden">
		<form action="registerRequirement" method="POST">
			generico : <input name="requirement.title" placeholder="Título"
				type="text"> Descrição : <input
				name="requirement.description" placeholder="Descrição" type="text">
			<input type="submit" value="Login">
		</form>
	</div>

	<div id="formHistoria" style="visibility:hidden">
		<form action="registerRequirement" method="POST">
			historia : <input name="requirement.title" placeholder="Título"
				type="text"> Descrição : <input
				name="requirement.description" placeholder="Descrição" type="text">
			<input type="submit" value="Login">
		</form>
	</div>
	
	<div id="formCasoDeUso" style="visibility:hidden">
		<form action="registerRequirement" method="POST">
			caso de uso : <input name="requirement.title" placeholder="Título"
				type="text"> Descrição : <input
				name="requirement.description" placeholder="Descrição" type="text">
			<input type="submit" value="Login">
		</form>
	</div>
</body>
</html>

