<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="/WEB-INF/layouts/header.jsp" %>

	<div class="col-md-6 col-md-offset-3 notify">
		<i class="glyphicon glyphicon-envelope"></i>
		<h2><fmt:message key="successful_sign_in"/></h2>
		<div class="alert alert-success" role="alert">
		<p>
			<fmt:message key="require_email_confirm"/>
		</p>
		</div>
	</div>

<%@ include file="/WEB-INF/layouts/footer.jsp" %>
