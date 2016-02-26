<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">
<link href="<c:url value='/css/kanban.css'/>" rel="stylesheet">
<script src="<c:url value='/js/jquery.js'/>"></script>
<title>Kanban</title>
</head>
<body>
	<c:forEach items="${layerList}" var="layer">
		<div class="layer" ondrop="drop(event, ${layer.layerID})"
			ondragover="allowDrop(event)" id="div1">
			<h2>${layer.name}</h2>
			<c:forEach items="${requirements}" var="requirement">
				<c:if test="${requirement.layer.layerID == layer.layerID}">
					<div class="requirement ${requirement}" id="drag${requirement.id}"
					 	draggable="true" ondragstart="drag(event)">
						${requirement.title}
					</div>
				</c:if>
			</c:forEach>
		</div>
	</c:forEach>
	<script type="text/javascript" src="<c:url value='/js/kanban.js'/>"></script>
</body>
</html>