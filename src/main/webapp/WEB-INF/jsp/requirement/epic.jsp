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
		<form action="createEpic" method="POST">
			<input name="epic.title" class="form-control" placeholder="Title" type="text">
			<input name="epic.description" class="form-control" placeholder="Description" type="text">
			<input name="epic.content" class="form-control" placeholder="Content" type="text">
			<input type="submit" value="Add" class="btn btn-success btn-group-justified">
		</form>
	</div>
</body>
</html>