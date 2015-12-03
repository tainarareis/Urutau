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
	<div id=create-project class="project-box">
		<form action="createProject" method="POST">
			<input name="project.title" placeholder="Title" type="text" class="form-control">
			<br/>
			<select class="form-control">
				<option value="0" selected="selected">Development Processs</option>
				<option value="Scrum">Scrum</option>
				<option value="Rational Unified Process">Rational Unified Process</option>
				<option value="Generic">Generic</option>
			</select>
			<br/>
			<input name="project.description" placeholder="Description" type="text" class="form-control">
			<br/> 
			<input type="submit" value="Add" class="btn btn-success btn-group-justified">
		</form>
        <br/>		
	</div>
</body>
</html>