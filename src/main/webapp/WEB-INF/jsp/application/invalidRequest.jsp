<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	
	<div class="col-md-6 col-md-offset-3 notify">
		<i class="glyphicon glyphicon-remove-circle"></i>
		<h2><fmt:message key="invalid_request"/></h2>
		<p><fmt:message key="possible_causes"/></p>
		<ul class="errors-list">
			<c:forEach var="error" items="${errors}">
				<li class="alert alert-danger" role="alert">
					${error.message} 
				</li>
			</c:forEach>		
		</ul>
	</div>