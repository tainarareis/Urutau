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
    
    <!-- Stylesheet -->
    <link href="<c:url value='/css/stylesheet.css'/>" rel="stylesheet">
    
    <!-- Custom CSS 2-->
    <link href="<c:url value='css/sb-admin.css'/>" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<c:url value='font-awesome/css/font-awesome.min.css'/>" rel="stylesheet" type="text/css">

    <!-- Plugin JavaScript -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
    <script src="js/classie.js"></script>
    <script src="js/cbpAnimatedHeader.js"></script>


    <!-- jQuery -->
    <script src="js/jquery.js"></script>
    
	<script type="text/javascript" src="<c:url value='/js/jquery.js'/>"></script>
	<script>
		function changeFormType() {
			var option = $("#requirementType").val();
			
			// Set all divs to invisible
			$("#generic").css('visibility', 'hidden');
			$("#storie").css('visibility', 'hidden');
			$("#useCase").css('visibility', 'hidden');
			$("#epic").css('visibility', 'hidden');
			$("#feature").css('visibility', 'hidden');
			
			if (option == "generic") {
				$("#generic").css('visibility', 'visible');
			} else if (option == "storie") {
				$("#storie").css('visibility', 'visible');
			} else if (option == "useCase") {
				$("#useCase").css('visibility', 'visible');
			} else if (option == "epic") {
				$("#epic").css('visibility', 'visible');
			} else if (option == "feature") {
				$("#feature").css('visibility', 'visible');
			} else {
				// Do nothing
			}
		}
	</script>
</head>

<body>

	
	<jsp:include page='header.html'/>
	
	<div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Cadastro de Requisitos <small></small>
                        </h1>
                        <ol class="breadcrumb">
                            <li class="active">
                                <i class="fa fa-dashboard"></i> Selecione o tipo de requisito desejado
                            </li>
                        </ol>
                    </div>
                </div>
                <!-- /.row -->
	
	<h1>Cadastro de Requisitos</h1> 
		<div class="dropdown">
  			<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Selecione o tipo de requisito desejado
  			<span class="caret"></span></button>
  				<select id="requirementType" onchange="changeFormType()">
			<ul class="dropdown-menu">	
					<a href="#"></a><option value="nenhum">Nenhum</option></a>
					<option value="generic">Genérico</option>
					<option value="storie">História de Usuário</option>
					<option value="useCase">Caso de Uso</option>
					<option value="epic">Épico</option>
					<a href="#"><option value="feature">Feature</option>
			</ul>
		
				</select>
			
		<div id="generic" class="requirement-box">
			<form action="createGeneric" method="POST">
				<input name="generic.title" placeholder="Título" type="text">
				<br/>
				<input name="generic.description" placeholder="Descrição" type="text">
				<br/>
				<input type="submit" value="Cadastrar">
			</form>
		</div>
	
		<div id="storie" class="requirement-box">
			<form action="createStorie" method="POST">
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
			<form action="createUseCase" method="POST">
				<input name="useCase.title" placeholder="Título" type="text">
				<input name="useCase.description" placeholder="Descrição" type="text">
				<input name="useCase.actors" placeholder="Atores" type="text">
				<input type="submit" value="Cadastrar">
			</form>
		</div>
		
		<div id="epic" class="requirement-box">
			<form action="createEpic" method="POST">
				<input name="epic.title" placeholder="Título" type="text">
				<input name="epic.description" placeholder="Descrição" type="text">
				<input name="epic.content" placeholder="Conteudo" type="text">
				<input type="submit" value="Cadastrar">
			</form>
		</div>
		
		<div id="feature" class="requirement-box">
			<form action="createFeature" method="POST">
				<input name="feature.title" placeholder="Título" type="text">
				<input name="feature.description" placeholder="Descrição" type="text">
				<input name="feature.content" placeholder="Conteudo" type="text">
				<input type="submit" value="Cadastrar">
			</form>
		</div>
	</div>
	
	</div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
</body>
</html>