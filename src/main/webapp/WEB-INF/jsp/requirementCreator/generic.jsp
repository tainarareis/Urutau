<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"
	integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ=="
	crossorigin="anonymous">

<style type="text/css">
.form-control {
	margin-bottom: 10px;
}
</style>
</head>
<body>
	<div class="requirement-box form-group">
		<form action="createGeneric" method="POST">
			<input name="generic.title" placeholder="Title" type="text" class="form-control" >
			<input name="generic.description" placeholder="Description" type="text" class="form-control" > 
			<input type="submit" value="Add" class="btn btn-success btn-group-justified">
		</form>
	</div>
</body>
</html>