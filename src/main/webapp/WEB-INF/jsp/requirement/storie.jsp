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
	<div id="storie" class="requirement-box">
			<form action="createStorie" method="POST">
				<input  type="text" name="storie.title" placeholder="Title" class="form-control">
				<label>Tell the Story:</label>
				<textarea name="storie.history" class="form-control">
				
				</textarea>
				<input type="text" name="acceptanceCriteria.content" class="form-control" placeholder="Acceptance criteria">
				<input type="submit" value="Add" class="btn btn-success btn-group-justified">
			</form>
		</div>
</body>
</html>