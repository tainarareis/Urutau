<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	
	<title>Urutau - Home page</title>

    <!-- Bootstrap Core CSS -->
    <link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<c:url value='/css/agency.css'/>" rel="stylesheet">
    
    <!-- Stylesheet -->
    <link href="<c:url value='/css/stylesheet.css'/>" rel="stylesheet">
    
    <link href="<c:url value='/css/index.css'/>" rel="stylesheet">
    
    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
</head>

<body id="page-top" class="index">
    <!-- Header -->
    <header>
        <div class="intro-text">
            <div class="col-xs-8"> 
	            <div class="intro-lead-in"> 
	            	<fmt:message key="urutau.welcome"/> <br/>
	            	<small><fmt:message key="urutau.description"/></small>
	            	<br/>
	            	<br/>            	
		            <a href="#register" class="page-scroll"><fmt:message key="sign_in"/></a>
	            </div>
	    	</div>
            <div class="col-xs-3">                 
	            <c:forEach var="error" items="${errors}">
	            	<!-- Show only login errors -->
	            	<c:if test="${error.category == 'loginError'}">
		    			<span class="error">${error.message}</span> <br />
		    		</c:if>
				</c:forEach>
	            <form action="<c:url value='user/authenticate'/>" class="form-signin" method="POST">
					<input name="login" class="form-control" placeholder="<fmt:message key='user.login'/>" required>
					<input name="password" type="password" class="form-control" placeholder="<fmt:message key='user.password'/>" required>
					<button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key='sign_up'/></button>
				</form>
     		</div>
        </div>
    </header>
    
 <section id="register" class="home-section">
	 <div class="container">
		<div class="col-md-6 col-md-offset-3">
			<c:forEach var="error" items="${errors}">
            	<c:if test="${error.category == 'error-message'}">
			    	<span class="error">${error.message}</span> <br />
			    </c:if>
			</c:forEach>
			<h3><fmt:message key='sign_up'/></h3>
			<form action="register" class="form-signin" method="POST">		
				<input name="user.email" type="email" class="form-control" 
					placeholder="<fmt:message key='user.email'/>" required autofocus>
					
				<input name="user.name" class="form-control" 
					placeholder="<fmt:message key='user.name'/>" required>
					
				<input name="user.lastName" class="form-control" 
					placeholder="<fmt:message key='user.lastname'/>" required>
					
				<input name="user.login" class="form-control" 
					placeholder="<fmt:message key='user.login'/>" required>
					
				<input name="user.password" type="password" class="form-control" 
					placeholder="<fmt:message key='user.password'/>" required>
					
				<input name="user.passwordVerify" type="password" class="form-control" 
					placeholder="<fmt:message key='user.password_verify'/>" required>
					
				<p class="info">
					The registration rules depend  of the enterprise policy. <br/>
					More information: <a href="#contact" class="page-scroll">Contact administrator</a>.
				</p>
				
				<input class="btn btn-lg btn-primary btn-block" type="submit" value="<fmt:message key='sign_in'/>">
			</form>
		</div>
	</div>
</section>
<br/>
<!-- Contact Section -->
    <section id="contact">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading"><fmt:message key='contact'/></h2>
                    <h3 class="section-subheading text-muted"><fmt:message key='contact.description'/></h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <form name="sentMessage" id="contactForm" novalidate>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Name *" id="name" 
                                    	required data-validation-required-message="Please, insert your name">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="email" class="form-control" placeholder="E-mail*" id="email" 
                                    	required data-validation-required-message="Please, insert your e-mail">
                                    <p class="help-block text-danger"></p>
                                </div>
                                <div class="form-group">
                                    <input type="tel" class="form-control" placeholder="Phone *" id="phone" 
                                    	required data-validation-required-message="Please, insert your number">
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <textarea class="form-control" placeholder="Message *" id="message" 
                                    	required data-validation-required-message="Insert your message to administrator!"
                                    	rows="6"></textarea>
                                    <p class="help-block text-danger"></p>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-lg-12 text-center">
                                <div id="success"></div>
                                <button type="submit" class="btn btn-xl">Send</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    
    <%@ include file="/WEB-INF/layouts/footer.jsp"%>
    
</body>

</html>
