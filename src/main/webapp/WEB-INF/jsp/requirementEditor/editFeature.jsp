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
			<input name="epic.title" class="form-control" placeholder="Title" type="text" value="${feature.title}">
			<input name="epic.description" class="form-control" placeholder="Description" type="text" value="${feature.description}">
			<input name="epic.content" class="form-control" placeholder="Content" type="text" value="${feature.content}">
			<input type="submit" value="Save" class="btn btn-success btn-group-justified">
		</form>
	</div>
</body>
</html>