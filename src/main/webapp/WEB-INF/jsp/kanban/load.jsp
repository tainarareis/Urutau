<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript" src="<c:url value='/js/kanban.js'/>"></script>
<link href="<c:url value='/css/kanban.css'/>" rel="stylesheet">
	
<div class="row">
	<!--
	
	<form action='<c:url value="/kanban/createLayer"/>' method="POST">
		<input type="hidden" name="projectID" value="${projectID}">
		<input type="text" name="layer.name" placeholder="Layer name">
		<input type="submit" value="Create layer">
	</form>
	
	-->
	
	<c:forEach var="error" items="${errors}">
    	<!-- Show only login errors -->
       	<c:if test="${error.category == 'loginError'}">
			<span class="error">${error.message}</span> <br />
  		</c:if>
	</c:forEach>
	
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
</div>
