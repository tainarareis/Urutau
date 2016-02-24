<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">
<link href="<c:url value='/css/kanban.css'/>" rel="stylesheet">
<title>Layer Load</title>
</head>
<body>
	<c:forEach items="${layerList}" var="layer">
		<div class="layer layer-${layer.layerID}">
			<h2>${layer.name}</h2>
		</div>
	</c:forEach>
</body>
</html>