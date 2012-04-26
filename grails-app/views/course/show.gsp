<%@ page import="survey.Person" %>
<%@ page import="survey.Course" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
        <g:set var="isAdmin" value="${Person.get(session['user']).isAdmin}"/> 
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <g:if test="${isAdmin}"> 
            	<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            	<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        	</g:if>
        </div>
        <div class="body">
            <h1>${courseInstance.name}</h1>
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                                                         
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="course.abbreviation.label" default="Abbreviation" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: courseInstance, field: "abbreviation")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="course.term.label" default="Term" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: courseInstance, field: "term")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="course.year.label" default="Year" /></td>
                            
                            <td valign="top" class="value"><g:formatNumber number="${courseInstance.year}" groupingUsed="false"/></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Instructor:</td>
                            
                            <td valign="top" class="value"><g:link controller="person" action="show" id="${courseInstance?.owner?.id}">${courseInstance?.owner?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name">Enrolled Students:</td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                            	<g:if test="${isAdmin}">
                            		<!-- Couldn't be done practically in separate css file -->
                            		<style type="text/css">a:hover { text-decoration: none; }</style>
	                            	<g:link controller="enrollment"
	                            			action="create"
	                            			params="${['course.id': courseInstance.id] }" >
		                            	<trinkets:addButton id="addStudentButton"
		                            						title="Add or remove a student"
		                            						width="145px" >
		                            		Add or remove a student
	                            		</trinkets:addButton>
	                            	</g:link>
	                            	<br />
                                </g:if>
                                <ul>
                                <g:each in="${courseInstance.enrollments}" var="e">
                                    <li>
                                    	<trinkets:protectedLink controller="person" action="show" id="${e.person.id}" link="${isAdmin}">
                                    		${e?.person?.encodeAsHTML()}
                                    	</trinkets:protectedLink>
                                    </li>
                                </g:each>
                                </ul>
                            </td>
                        </tr>
                        
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
            		<g:if test="${isAdmin}">
	                    <g:hiddenField name="id" value="${courseInstance?.id}" />
		                <span class="button">
		                  	<g:actionSubmit class="edit"
		                   					action="edit"
		                   					value="${message(code: 'default.button.edit.label', default: 'Edit')}" />
		                </span>
	                   	<span class="button">
	                   		<g:actionSubmit class="delete"
	                   						action="delete"
	                   						value="${message(code: 'default.button.delete.label', default: 'Delete')}"
	                   						onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	                   	</span>
                   	</g:if>
                   	<g:else>
                   		<trinkets:fakeButton />
                   	</g:else>
                </g:form>
            </div>
            <br/>
            
            <g:if test="${courseInstance.projects.size() != 0}">
		        <h1>${courseInstance.toString()} Projects</h1>
				<div class="list">
					<table>
						<thead>
							<tr>
								<g:sortableColumn property="name"
									title="${message(code: 'course.project.label', default: 'Projects')}" />
										
								<th><g:message code="course.project.groups.label"
										default="No. of Groups" /></th>
										
							</tr>
						</thead>
						<tbody>
							<g:each in="${courseInstance.projects}" status="i" var="k">
								<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
		
									<td>
										<g:link controller="project" action="show" id="${k.id}">
											${k?.encodeAsHTML()}
										</g:link>
									</td>
		
									<td valign="top" style="text-align: left;" class="value">
										${k.teams.size()}
									</td>			
								</tr>
							</g:each>
						</tbody>
					</table>
					<div class="buttons">
						<g:form>
							<g:if test="${isAdmin}">
			                    <span class="button">
			                    	<g:link class="create" controller="project" action="create" params="${['course.id': courseInstance.id]}">
			                    		<input class="add" value="Add Projects"/>
			                    	</g:link>
			                    </span>
		                    </g:if>
		                   	<g:else>
		                   		<trinkets:fakeButton />
		                   	</g:else>
		                </g:form>
					</div>
		        </div>
	        </g:if>
	        <g:else>
				<g:if test="${isAdmin}">
					<h2><g:link controller="project" action="create" params="${['course.id': courseInstance.id]}">No projects have been created. Click here to add a project</g:link>
					</h2>
				</g:if>
			</g:else>
        </div>
    </body>
</html>
