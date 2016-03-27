<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Invalid request</title>
</head>
<body>
	<div class="col-md-6 col-md-offset-3 invalid-request">
		<i class="glyphicon glyphicon-remove-circle"></i>
		<p>You have made a invalid request</p>
		<p>Possible causes:</p>
		<ul class="errors-list">
			<c:forEach var="error" items="${errors}">
				<li class="alert alert-danger" role="alert">
					${error.message} 
				</li>
			</c:forEach>		
		</ul>
	</div>
</body>
</html>