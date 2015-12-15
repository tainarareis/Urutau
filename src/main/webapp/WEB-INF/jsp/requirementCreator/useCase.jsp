<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
<style type="text/css">
.form-control {
	margin-bottom: 10px;
}
</style>
</head>
<body>
	<div id="useCase" class="requirement-box">
		<form action="createUseCase" method="POST">
			<input name="useCase.projectID" type="hidden" value="${projectID}">
			<input name="useCase.title" class="form-control" placeholder="Title" type="text">
			<input name="useCase.description" class="form-control" placeholder="Description" type="text">
			<input name="useCase.fakeActors" class="form-control" placeholder="Separated by ','" type="text">
			<input type="submit" value="Add" class="btn btn-success btn-group-justified">
		</form>
	</div>
</body>
</html>