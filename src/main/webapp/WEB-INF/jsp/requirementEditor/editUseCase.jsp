<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="requirement-box">
	<h2><fmt:message key="edit"/> use case</h2>
	<form action="editUseCase" method="POST">
		<input name="useCase.title" class="form-control" 
			placeholder="Title" type="text" value="${useCase.title}">
		<input name="useCase.description" class="form-control" 
			placeholder="Description" type="text" value="${useCase.description}">
		<input name="useCase.fakeActors" class="form-control" 
			placeholder="Separated by ','" type="text" value="${useCase.description}">
		<button type="submit" class="btn btn-success btn-group-justified">
			<fmt:message key="save"/>
		</button>
	</form>
</div>