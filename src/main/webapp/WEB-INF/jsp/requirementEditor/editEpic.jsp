<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="requirement-box">
	<h2><fmt:message key="edit"/> epic</h2>
	<form action="editEpic" method="POST">
		<input name="epic.title" class="form-control" placeholder="Title" type="text" value="${epic.title}">
		<input name="epic.description" class="form-control" placeholder="Description" type="text" value="${epic.description}">
		<input name="epic.content" class="form-control" placeholder="Content" type="text" value="${epic.content}">
		<button type="submit" class="btn btn-success btn-group-justified">
			<fmt:message key="save"/>
		</button>
	</form>
</div>