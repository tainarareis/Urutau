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
	<div id="epic" class="requirement-box">
		<form action="editEpic" method="POST">
			<input name="useCase.title" class="form-control" 
				placeholder="Title" type="text" value="${useCase.title}">
			<input name="useCase.description" class="form-control" 
				placeholder="Description" type="text" value="${useCase.description}">
			<input name="useCase.fakeActors" class="form-control" 
				placeholder="Separated by ','" type="text" value="${useCase.description}">
			<input type="submit" value="Save" class="btn btn-success btn-group-justified">
		</form>
	</div>
</body>
</html>