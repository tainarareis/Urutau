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
	    	<h4><i class="glyphicon glyphicon-plus"></i> Storie</h4>
	    </div> 
		<div id="storie" class="requirement-box">
			<form action="requirement/createUserStory" method="POST">		
				<input name="storie.projectID" type="hidden" value="${projectID}">
				<input type="text" name="storie.title" placeholder="Title" class="form-control">
				<label>Tell the Story:</label>
				<textarea name="storie.history" class="form-control">
				
				</textarea>
				<input type="text" name="acceptanceCriteria.content" class="form-control" placeholder="Acceptance criteria">
				<input type="submit" value="Add" class="btn btn-success btn-group-justified">
			</form>
		</div>
	</div>
</body>
</html>