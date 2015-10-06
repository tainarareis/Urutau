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
    
    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Plugin JavaScript -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
    <script src="js/classie.js"></script>
    <script src="js/cbpAnimatedHeader.js"></script>

	<script type="text/javascript" src="<c:url value='/js/jquery.js'/>"></script>
	<script>
		function changeFormType() {
			var option = $("#requirementType").val();
			
			// Set all divs to invisible
			$("#generic").css('visibility', 'hidden');
			$("#storie").css('visibility', 'hidden');
			$("#useCase").css('visibility', 'hidden');
			
			if (option == "generic") {
				$("#generic").css('visibility', 'visible');
			} else if (option == "storie") {
				$("#storie").css('visibility', 'visible');
			} else if (option == "useCase") {
				$("#useCase").css('visibility', 'visible');
			} else {
				// Do nothing
			}
		}
	</script>
</head>

<body id="page-top" class="index">
	<div class="container">
		<h1>Novo requisito</h1>
	
		<select id="requirementType" onchange="changeFormType()">
			<option value="nenhum">Nenhum</option>
			<option value="generic">Genérico</option>
			<option value="storie">História de Usuário</option>
			<option value="useCase">Caso de Uso</option>
		</select>
	
		<div id="generic" class="requirement-box">
			<form action="registerRequirement" method="POST">
				<input name="requirement.title" placeholder="Título" type="text">
				<input name="requirement.description" placeholder="Descrição" type="text">
				<input type="submit" value="Cadastrar">
			</form>
		</div>
	
		<div id="storie" class="requirement-box">
			<form action="registerRequirement" method="POST">
				<input name="storie.title" placeholder="Título" type="text">
				<br/> 
				<textarea name="storie.history" placeholder="Descrição">
				</textarea>
				<br/> 
				<input name="acceptanceCriteria.content" placeholder="Critérios de aceitação" type="text">
				<br/> 
				<input type="submit" value="Cadastrar">
			</form>
		</div>
	
		<div id="useCase" class="requirement-box">
			<form action="registerRequirement" method="POST">
				<input name="useCase.title" placeholder="Título" type="text">
				<input name="useCase.description" placeholder="Descrição" type="text">
				<input name="useCase.actors" placeholder="Atores" type="text">
				<input type="submit" value="Cadastrar">
			</form>
		</div>
	</div>
</body>
</html>