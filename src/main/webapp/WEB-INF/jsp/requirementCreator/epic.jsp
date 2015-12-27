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
	    	<h4><i class="glyphicon glyphicon-plus"></i> Epic</h4>
	    </div> 
		<div id="epic" class="requirement-box">
			<form action="requirement/createEpic" method="POST">
				<input name="epic.projectID" type="hidden" value="${projectID}">
				<input name="epic.title" class="form-control" placeholder="Title" type="text">
				<input name="epic.description" class="form-control" placeholder="Description" type="text">
				<input name="epic.content" class="form-control" placeholder="Content" type="text">
				<input type="submit" value="Add" class="btn btn-success btn-group-justified">
			</form>
		</div>
	</div>
</body>
</html>