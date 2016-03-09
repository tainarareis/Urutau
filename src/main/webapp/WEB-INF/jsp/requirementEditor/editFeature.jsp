<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="requirement-box">
	<h2><fmt:message key="edit"/> feature</h2>
	<form action="editFeature" method="POST">
		<input name="feature.title" class="form-control" placeholder="Title" type="text" value="${feature.title}">
		<input name="feature.description" class="form-control" placeholder="Description" type="text" value="${feature.description}">
		<input name="feature.content" class="form-control" placeholder="Content" type="text" value="${feature.content}">
		<button type="submit" class="btn btn-success btn-group-justified">
			<fmt:message key="save"/>
		</button>
	</form>
</div>