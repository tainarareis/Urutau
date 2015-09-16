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
	
	<title>Urutau - Página Inicial</title>

    <!-- Bootstrap Core CSS -->
    <link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<c:url value='/css/agency.css'/>" rel="stylesheet">
    
    <!-- Stylesheet -->
    <link href="<c:url value='/css/stylesheet.css'/>" rel="stylesheet">
    
    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
    <script src="js/classie.js"></script>
    <script src="js/cbpAnimatedHeader.js"></script>

    <!--
    	FIX ME - ISSUE https://github.com/Modesteam/Urutau/issues/3
    Contact Form JavaScript 
    <script src="js/jqBootstrapValidation.js"></script>
    <script src="js/contact_me.js"></script>
    -->

    <!-- Custom Theme JavaScript -->
    <script src="js/agency.js"></script>    
</head>

<body id="page-top" class="index">
    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand page-scroll" href="#page-top">
                	<img src="img/Urutau.png" class="img-responsive" width="70px">               
	            </a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li class="hidden">
                        <a href="#page-top"></a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#about">Sobre</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#team">Time</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="#contact">Contato</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>

    <!-- Header -->
    <header>
        <div class="intro-text">         
            <div class="col-xs-8"> 
	            <div class="intro-lead-in"> 
	            	Bem Vindo ao Urutau! <br/>
	            	<small>Sua ferramenta de requisitos open source!</small>
	            </div>
	    	</div>
            <div class="col-xs-3">                 
	            <form action="login" class="form-signin" method="POST">
					<input name="user.login" id="inputLogin" class="form-control" placeholder="Login" required>
					<input name="user.password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
					<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
				</form>              
	            <br/>
	            Ainda não é membro? <a href="#cadastrar" class="page-scroll">Cadastra-se</a>
        	</div>
        </div>
    </header>
    
 <section id="cadastrar">
	 <div class="container">
		<div class="col-md-6 col-md-offset-3">
			<c:forEach var="error" items="${errors}">
			    ${error.message}<br />
			</c:forEach>
			<h3>Sign up</h3>
			<form action="register" class="form-signin" method="POST">		
				<input name="user.email" type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
				<input name="user.name" id="inputName" class="form-control" placeholder="Name" required>
				<input name="user.lastName" id="inputLastName" class="form-control" placeholder="Last name" required>
				<input name="user.login" id="inputLogin" class="form-control" placeholder="Login" required>
				<input name="user.password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
				<input name="user.passwordVerify" type="password" id="inputPassword" class="form-control" placeholder="Confirm password" required>
				<input class="btn btn-lg btn-primary btn-block" type="submit" value="Cadastrar">
			</form>
			<p class="info">
				The registration rules depend  of the enterprise policy. <br/>
				More information: <a href="#contact" class="page-scroll">Contact administrator</a>.
			</p>
		</div>
	</div>
</section>

<!-- Contact Section -->
    <section id="contact">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Contato</h2>
                    <h3 class="section-subheading text-muted">Tire suas dúvidas sobre o Urutau</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <form name="sentMessage" id="contactForm" novalidate>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Nome *" id="name" required data-validation-required-message="Por gentileza, insira seu nome.">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="email" class="form-control" placeholder="E-mail*" id="email" required data-validation-required-message="Por gentileza, insira seu e-mail">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="tel" class="form-control" placeholder="Telefone*" id="phone" required data-validation-required-message="Por gentileza, insira seu telefone">
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <textarea class="form-control" placeholder="Mensagem *" id="message" required data-validation-required-message="Por gentileza, insira a mensagem que deseja enviar para nós."></textarea>
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-lg-12 text-center">
                                <div id="success"></div>
                                <button type="submit" class="btn btn-xl">Enviar Mensagem</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>

    <footer>
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <span class="copyright">Copyright &copy; URUTAU 2015</span>
                </div>
                <div class="col-md-4">
                    <ul class="list-inline social-buttons">
                        <li><a href="#"><i class="fa fa-twitter"></i></a>
                        </li>
                        <li><a href="#"><i class="fa fa-facebook"></i></a>
                        </li>
                        <li><a href="#"><i class="fa fa-linkedin"></i></a>
                        </li>
                    </ul>
                </div>
                <div class="col-md-4">
                    <ul class="list-inline quicklinks">
                        <li><a href="#">Política de Privacidade</a>
                        </li>
                        <li><a href="#">Termos de Uso</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </footer>
</body>

</html>
