<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="requirement-box">
	<h2><fmt:message key="edit"/> user story</h2>
	<form action="editUserStorie" method="POST">
		<input name="userStorie.title" class="form-control" placeholder="Title" type="text" value="${userStorie.title}">
		<input name="userStorie.description" class="form-control" placeholder="Description" type="text" value="${userStorie.description}">
		<input name="userStorie.content" class="form-control" placeholder="Content" type="text" value="${userStorie.content}">
		<button type="submit" class="btn btn-success btn-group-justified">
			<fmt:message key="save"/>
		</button>
	</form>
</div>