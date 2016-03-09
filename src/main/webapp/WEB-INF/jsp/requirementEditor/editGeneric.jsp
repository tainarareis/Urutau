<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="requirement-box">
	<form action="editGeneric" method="POST">
		<input name="generic.title" class="form-control" placeholder="Title" type="text" value="${generic.title}">
		<input name="generic.description" class="form-control" placeholder="Description" type="text" value="${generic.description}">
		<button type="submit" class="btn btn-success btn-group-justified">
			<fmt:message key="save"/>
		</button>
	</form>
</div>