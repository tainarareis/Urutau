<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Lista de Requisitos</title>
<meta name="generator" content="Bootply" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link
	href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.min.css"
	rel="stylesheet">
<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
<link href="css/styles.css" rel="stylesheet">
</head>
</head>
<body>


	<div>
		<table id="tbArtifact" class="table table-striped">
		  <thead>
		  	<tr>
		  		<th class="col-md-6" >Título</th>
		  	</tr>
		  </thead>
		  <tbody id="bodyTbArtifact">
		  	<c:forEach items="${artifact}" var="artifact" >
		  		<tr>
		  			<td>${artifact.title}</td>		  			
		  			<td><a href="${artifact.id}/${artifact.title}" class="btn btn-info btn-lg">Detalhar</a></td>
		  			<td><a href="/modifyRequirement" class="btn btn-info btn-lg">Alterar</a></td>
		  			<td>
		  				<form action="excludeRequirement" method="POST">		  					
		  					<input name='artifact.id' value="${artifact.id}" type="hidden"/>
		  					<input type="submit" value="Excluir"/>
		  				</form>
		  			</td>		  				
		  		</tr>
		  	</c:forEach>
		  </tbody>
		</table>
		
		<a href="." class="btn btn-info btn-lg">Voltar</a>
		
	</div>
</body>
</html>