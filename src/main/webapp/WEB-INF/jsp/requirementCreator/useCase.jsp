<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body>
	<div class="modal-content" >
		<div class="modal-header">
	    	<h4><i class="glyphicon glyphicon-plus"></i> Use case</h4>
	    </div> 
		<div id="useCase" class="requirement-box">
			<form action="requirement/createUseCase" method="POST">
				<input name="useCase.projectID" type="hidden" value="${projectID}">
				<input name="useCase.title" class="form-control" placeholder="Title" type="text">
				<input name="useCase.description" class="form-control" placeholder="Description" type="text">
				<input name="useCase.fakeActors" class="form-control" placeholder="Separated by ','" type="text">
				<input type="submit" value="Add" class="btn btn-success btn-group-justified">
			</form>
		</div>						
	</div>
</body>
</html>