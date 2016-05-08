<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="row">
	<div class="navbar-default sidebar" role="navigation">
		<div class="col-md-3">
		
		</div>
	</div>

	<div class="col-md-5">
		<h1>${project.title} Settings</h1>
		<form action="<c:url value="/${project.id}/setting"/>" method="POST" class="form-group">
			<label for="title">Title</label>
			<input type="text" name="project.title" value="${project.title}"
				class="form-control" placeholder="Title" id="title">

			<label for="desc">Brief description</label>				
			<input type="text" name="project.description" value="${project.description}"
				class="form-control" placeholder="Simple description" id="desc"/>

			<div class="checkbox">
				<label>
					<input type="checkbox" name="project.public" id="privacy" 
						<c:if test="${project.isPublic() == true}">checked="checked"</c:if>/>
						Projeto é publico?
				</label>
			</div>
			
			<a href="<c:url value="/${project.id}-${project.title}"/>" class="btn btn-default">Cancel</a>
			<button type="submit" name="_method" value="PUT" class="btn btn-success">Update</button>
		</form>
	</div>
</div>

</body>
</html>