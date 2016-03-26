<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${not empty errors}">
	<div class="alert alert-danger" role="alert">
		<ul class="errors-list">
			<c:forEach var="error" items="${errors}">
				<li>${error.message}</li>
			</c:forEach>
		</ul>
		<br>
	</div>
</c:if>