<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<body>
	<div>
		<table class="table table-striped">
	  		<c:forEach items="${requirements}" var="requirement" >
		  		<tr>
		  			<td>${requirement.title}</td>
		  			<td>
		  				${requirement.author.login}
		  			</td>
		  			<td>
		  				 <fmt:formatDate value="${requirement.dateOfCreation.time}" pattern="dd/MM/yyyy"/>
		  			</td>
		  			<td>
		  				<a href="${requirement.id}/${requirement.title}">See</a>
		  			</td>
		  			<td>
		  				<a href="editRequirement/${requirement.id}">Edit</a>
		  			</td>
		  			<td>
		  				<a href="excludeRequirement/${requirement.id}" title="Delete">
		  					<span class="glyphicon glyphicon-remove"></span>
		  				</a>
		  			</td>		  				
		  		</tr>
	  		</c:forEach>
		 </table>
	</div>
</body>
</html>