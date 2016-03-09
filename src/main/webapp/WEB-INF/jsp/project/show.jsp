<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="row">
	<div class="navbar-default sidebar" role="navigation">
		<div class="col-md-3">
			<div class="sidebar-nav navbar-collapse">
				
				<ul class="nav requirement-create-type" id="side-menu">
					<li><a href="javascript:;" data-toggle="collapse"
						data-target="#demo" class="default-option"><fmt:message key="create_requirement"/></a>
						<ul id="demo" class="collapse list-unstyled suboption">
							<c:if test="${project.isGeneric()}">
								<li><a href="<c:url value="/requirement/generic"/>"
									class="link-create-r-modal" data-toggle="modal"
									data-target="#create-r-modal"> Generic Requirement </a></li>
							</c:if>

							<c:if test="${project.isGeneric() || project.isScrum()}">
								<li><a href="<c:url value="/requirement/storie"/>"
									class="link-create-r-modal" data-toggle="modal"
									data-target="#create-r-modal"> User Story </a></li>
							</c:if>

							<c:if test="${project.isGeneric() || project.isScrum()}">
								<li><a href="<c:url value="/requirement/feature"/>"
									class="link-create-r-modal" data-toggle="modal"
									data-target="#create-r-modal"> Feature </a></li>
							</c:if>

							<c:if test="${project.isGeneric() || project.isScrum()}">
								<li><a href="<c:url value="/requirement/epic"/>"
									class="link-create-r-modal" data-toggle="modal"
									data-target="#create-r-modal"> Epic </a></li>
							</c:if>

							<c:if test="${project.isGeneric() || project.isUP()}">
								<li><a href="<c:url value="/requirement/useCase"/>"
									class="link-create-r-modal" data-toggle="modal"
									data-target="#create-r-modal"> Use Case </a></li>
							</c:if>
						</ul>
					</li>

					<li id="link-show-kanban">
						<a href="<c:url value='kanban/${project.id}'/>"
						class="default-option">Kanban</a>
					</li>
					<li>
						<a href="#" class="default-option">
							<fmt:message key="settings"/>
						</a>
					</li>
					<li>
						<a href="#" class="default-option">
							<fmt:message key="activity"/>
						</a>
					</li>
				</ul>
				
				<div class="modal fade" id="create-r-modal" tabindex="-1"
					role="dialog">
					<div class="modal-dialog" id="r-form"></div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-9">
		<div class="create create-requirement panel panel-default">
			<div class="panel-heading">
				<h2 class="panel-title">
					<i class="glyphicon glyphicon-plus"></i> <fmt:message key="create_requirement"/>
				</h2>
			</div>

			<br />
			<div class="panel-footer">
				<button id="cancel-create-req" class="btn btn-warning">
					<fmt:message key="cancel"/>
				</button>
			</div>
		</div>

		<!-- Renderize message of success -->
		<c:if test="${message-success ne 0}">
			<div class="panel panel-success">
				<div class="panel-body bg-success">
					${message-success}
					<button type="button" class="close" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
			</div>
		</c:if>

		<div class="panel-target">
			<div class="panel panel-default">
				<div class="panel-body">
					<c:choose>
						<c:when test="${!project.isEmpty()}">
							<div class="panel-body requirements"></div>
						</c:when>
						<c:otherwise>
							<h2 class="text-center">
								<span class="glyphicon glyphicon-info-sign"></span>
								<fmt:message key="do_not_have_requirements"/>
							</h2>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Self page scripts -->
<script type="text/javascript">
	/*
	 * The follow script file needs page variable to work
	 */
	currentProjectID = ${project.id};

	var page = {
		projectID : currentProjectID,
		number : 0
	};
	/*
	 * Render paginate.jsp into div class requirements
	 */
	window.onload = function(event) {
		paginate();
	}
</script>

<script src="<c:url value='/js/requirement.js'/>" /></script>
</body>
</html>