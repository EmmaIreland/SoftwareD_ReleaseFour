<%@ page import="survey.Person" %>
<%@ page import="survey.Project" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <g:set var="isAdmin" value="${Person.get(session['user']).isAdmin}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>

        <script type="text/javascript">

		function deleteSurvey(image) {
			if (confirm("Are you sure you want to remove this survey from this project?")) {
				surveyId = $(image).parent().attr("id");
				jQuery.post("../../survey/delete/" , {id: surveyId});
				$(image).parent().remove();
			}
		}
        
        </script>
        
        
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <g:if test="${isAdmin}">
            	<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            </g:if>
        </div>
        <div class="body">
            <h1>Project: ${projectInstance.name}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    	<tr class="prop">
                            <td valign="top" class="name"><g:message code="project.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: projectInstance, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="project.course.label" default="Course" /></td>
                            
                            <td valign="top" class="value"><g:link controller="course" action="show" id="${projectInstance?.course?.id}">${projectInstance?.course?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                        
                        <tr class="prop">
                        	<td valign="top" class="name"><g:message code="project.dueDate.label" default="Due Date" /></td>
                        	
                        	<td valign="top" class="value"><g:formatDate date="${projectInstance?.dueDate}" format="MMMMM d, yyyy, h a" /></td>
                        	
                        </tr>                 
                       	
                       	<g:if test="${Person.get(session['user']).isAdmin}">  
                        <tr class="prop">
                            <td valign="top" class="name">Surveys:</td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                            	<g:link controller="survey" action="create" params="${['project.id': projectInstance.id] }">Create new Survey</g:link>
                                <g:each in="${projectInstance.surveys}" var="p">
                                    <span id="${p.id}">
                            		    <br><img src="../../images/delete.png" style="position: relative; top: 3px; cursor:pointer" onclick="deleteSurvey(this)" />
                            		    <g:link controller="survey" action="show" id="${p.id}">${p}</g:link>
                          		    </span>
                                </g:each>
                            </td>
                        </tr>
                    	</g:if>
                    	
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                	<g:if test="${isAdmin}">  
                    	<g:hiddenField name="id" value="${projectInstance?.id}" />
                    	<span class="button"><g:actionSubmit class="edit" 
                    										 action="edit" 
                    										 value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    	<span class="button"><g:actionSubmit class="delete" 
                    										 action="delete" 
                    										 value="${message(code: 'default.button.delete.label', default: 'Delete')}" 
                    										 onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    </g:if>
                    <g:else>
                   		<trinkets:fakeButton />
                   	</g:else>
                </g:form>
            </div>
            
            <g:if test="${projectInstance.teams.size() != 0}">
           	 	<h1>Groups in ${projectInstance.name}:</h1>
           	 	<g:if test="${isAdmin}">
           	 	<div id="projectNotes">
	           	 	<g:if test="${numUnassignedStudents > 0}">
	           	 		<div style="text-align: left; float: left">
	           	 			<h3><font color="red">Number of Students Unassigned to Groups: ${numUnassignedStudents}</font></h3>
	           	 		</div>
	           	 	</g:if>
	           	 	<div style="text-align: right;">
							<g:link controller="team" action="list" params="${[project: projectInstance.id]}"><h3>Manage Groups</h3></g:link>
					</div>
				</div>
				</g:if>
           	 	<div class="demo">
					
						<g:each in="${projectInstance.teams.sort{it.name}}" var="team">
							<trinkets:collapsibleDiv title="${team.name}">
		
							<div id="${team.id}">
								<g:if test="${isAdmin}">
								<h2>Comments:</h2>
								<p><trinkets:makeHTMLNewlines text="${team.comments}" /></p>
								<br></br>
								</g:if>
							
								<h2>Members:</h2>
								
								<g:each in="${team.memberships.member}" var="student">
									<ul>
										<li>
											<trinkets:protectedLink link="${isAdmin}" controller="person" action="show" id="${student.id}">
												${student?.encodeAsHTML()}
											</trinkets:protectedLink>
										</li>
									</ul>
								</g:each>
								
								<g:if test="${isAdmin}">
								<br></br>
								<div style="text-align: right;">
									<g:link class="edit" controller="team" action="edit" id="${team.id}"><h2>Edit Comments</h2></g:link>
									
								</div>
								</g:if>
							</div>
							</trinkets:collapsibleDiv>
						</g:each>
					</div>
					
			</g:if>	
			<g:else>
				<g:if test="${isAdmin}">
					<h2><g:link controller="team" action="list" params="${[project: projectInstance.id]}">No groups made. Click here to add groups</g:link></h2>
				</g:if>
			</g:else>
			
            
        </div>
    </body>
</html>
