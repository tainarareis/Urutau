<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hard errors happen</title>
</head>
<body>
<div class="alert alert-danger" role="alert">
	<ul class="errors-list">
		<c:forEach var="error" items="${errors}">
			<li>${error.message}</li>
		</c:forEach>
	</ul>
	<br>
</div>
</body>
</html>