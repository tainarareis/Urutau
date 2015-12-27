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
	    	<h4><i class="glyphicon glyphicon-plus"></i> Generic</h4>
	    </div> 
		<div class="requirement-box form-group">
			<form action="requirement/createGeneric" method="POST">
				<input name="generic.projectID" type="hidden" value="${projectID}">
				<input name="generic.title" placeholder="Title" type="text" class="form-control" >
				<input name="generic.description" placeholder="Description" type="text" class="form-control" > 
				<input type="submit" value="Add" class="btn btn-success btn-group-justified">
			</form>
		</div>
	</div>
</body>
</html>