
<%@ page import="survey.Team" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'team.label', default: 'Team')}" />
        <g:javascript src="utility.js" />
        <g:javascript src="teamscript.js" />
        <title>Manage Groups for ${projectInstance.name}</title>
		<g:javascript>
			$(function() {
				$("[name='newNamedGroupForm']").validate({
					errorPlacement: function(error, element) {
						error.insertAfter(element);  // default jquery.validation action.
					}, submitHandler: function(form) {
						form.submit();
					}
					
				});
			});
		</g:javascript>
        <link rel="stylesheet" href="${resource(dir:'css',file:'manageteams.css')}" />
    </head>

	  <body>
	  <div class="nav">
	      <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
	  </div>
	  <div class="body">
	      <h1>Manage Groups for <g:link class="uline" controller="project" action="show" id="${projectInstance.id}"> ${projectInstance.name}</g:link></h1>
	      <g:if test="${flash.message}">
	      <div class="message">${flash.message}</div>
	      </g:if>
	      	<div style="float: left;">
	      		<h2>Students not assigned to groups</h2>
	      		<div class="dialog droppable" id="unassignedStudents">
			 		<div title="Drag students to assign them to groups" id="studentList">
			 		<h2>&nbsp;Drag students to assign them to groups</h2>
			 		    <br>
			 			<g:if test="${courseHasStudents}">
		 					<ul class="students" id="g0" style="min-height: 40px">
		 						<g:each in="${unassignedStudents.sort() {it.name}}" var="student">
		 							<li><span class="student draggable" id="s${student.id}">${student.name}</span></li>
			 					</g:each>
			 				</ul>
		 				</g:if>
		 				<g:else>
			 				<g:link controller="enrollment" action="create" params="${['course.id': projectInstance.course.id]}">Add students to ${projectInstance.course.name}</g:link>
		 				</g:else>
		 			</div>
	 			</div>
	 			<trinkets:emptyButtonsBar />
 				<g:form action="save" name="newNamedGroupForm">
	           		<h2>Add new named group to this project:</h2>
               		<div class="dialog" id="addgroup" style="clear: left;">
	                   	<table>
	                       	<tbody>
	                       		<g:hiddenField name="project.id" value="${projectInstance.id}" />
	                           	<tr class="prop">
	                               	<td valign="top" class="name">
	                                   	<label for="name"><g:message code="team.name.label" default="Name" /></label>
	                               	</td>
	                               	<td valign="top" class="value ${hasErrors(bean: teamInstance, field: 'name', 'errors')}">
	                                   	<g:textField class="required" name="name" value="${teamInstance?.name}" />
										<label for="name" class="error" style="display:none;">
											<br/>Please enter a name for your group.
										</label>
	                              	</td>
	                           	</tr>                       
	                       	</tbody>
	                   	</table>
               		</div>
               		<div class="buttons">
                   		<span class="button"><g:submitButton name="create" class="save" value="Add" /></span>
               		</div>
           		</g:form>
	 			
 				<g:form action="addmany">
 					<h2>Add multiple generically-named groups:</h2>
 					<div class="dialog" id="addmultiplegroups" style="clear: left">
	 					<table>
	 						<tbody>
	 							<g:hiddenField name="project.id" value="${projectInstance.id}" />
	 							<tr class="prop">
	 								<td valign="top" class="name">Number of groups:</td>
	 								<td valign="top" class="value">
	 									<g:select from="${1..10}" name="num_groups" />
	 								</td>
	 							</tr>
	 							<tr class="prop">
	 								<td valign="top" class="name">Populate groups randomly:</td>
	 								<td>
	 									<g:checkBox name="random" value="${true}" />
	 								</td>
	 							</tr>
	 						</tbody>
	 					</table>
 					</div>
 					<div class="buttons">
                   		<span class="button"><g:submitButton name="create" class="save" value="Add" /></span>
               		</div>
 				</g:form>
	 			
	 		</div>
	 		<div title="Drag students to change groups" id="groupArea">
	 			<g:if test="${projectHasTeams}">
	 				<g:each in="${projectInstance.teams.sort() {it.name}}" var="team">
	 					<div class="group droppable">
	 						<img title="Delete Group" class="deleteButton" src="${resource(dir:'images',file:'delete.png')}"  />
	 						
	 						<div class="teamname">
	 							<g:link action="edit" id="${team.id}">${fieldValue(bean: team, field: "name")}</g:link>
	 						</div>
	 						
	 						<trinkets:collapsibleDiv title="Comments">
								<g:if test="${team.comments == null}">
									There are no comments for this group.	
								</g:if>
								<g:else>
									<trinkets:makeHTMLNewlines text="${team.comments}" />
								</g:else>
							</trinkets:collapsibleDiv>
	 						
	 						<ul class="students" id="g${team.id}">
	 							<g:each in="${team.memberships.sort() {it.member.name}}" var="membership">
	 								<li><span class="student draggable" id="s${membership.member.id}">${membership.member.name}</span></li>
	 							</g:each>
	 						</ul>
	 					</div>
	 				</g:each>
	 			</g:if>
	 			<g:else>
	 				<h2>No groups have been created for this project.</h2>
	 			</g:else>
	 		</div>
	 	</div>
	 	</body>
</html>