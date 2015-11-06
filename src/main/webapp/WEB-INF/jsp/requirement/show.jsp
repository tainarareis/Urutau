<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${artifact.title}</title>
	<!-- Bootstrap Core CSS -->
    <link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<c:url value='/css/agency.css'/>" rel="stylesheet">
    
    <!-- Stylesheet -->
    <link href="<c:url value='/css/stylesheet.css'/>" rel="stylesheet">
</head>
<body>
<div class="container">
	<h1>${artifact.title}</h1>
	<p>By ${artifact.author.name} in 
		<fmt:formatDate value="${artifact.dateOfCreation.time}" type="date" dateStyle="short" />
		</p>
	<h3>Description</h3>
	<p>${artifact.description}</p>

	<h3>Content</h3>
	<p>${artifact.content}</p>

</body>
</html>