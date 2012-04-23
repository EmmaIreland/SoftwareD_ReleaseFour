<%@ page import="survey.Person" %>
<%@ page import="survey.Course" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        	<g:if test="${Person.get(session['user']).isAdmin}">    
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
                        
                    	<g:if test="${Person.get(session['user']).isAdmin}">
                        <tr class="prop">
                            <td valign="top" class="name">Enrolled Students:</td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                            	<g:link controller="enrollment" action="create" params="${['course.id': courseInstance.id] }">Add or remove a student</g:link>
                                <ul>
                                <g:each in="${courseInstance.enrollments}" var="e">
                                    <li><g:link controller="person" action="show" id="${e.person.id}">${e?.person?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                        </tr>
                        </g:if>
                        
                    </tbody>
                </table>
                <g:if test="${addStudent}">
                
                </g:if>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${courseInstance?.id}" />
                    <g:if test="${Person.get(session['user']).isAdmin}">
                    	<span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                   		<span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                	</g:if>
                </g:form>
            </div>
            <br/>
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
						<g:if test="${Person.get(session['user']).isAdmin}">
	                    <span class="button">
	                    	<g:link class="create" controller="project" action="create" params="${['course.id': courseInstance.id]}">
	                    		<input class="add" value="Add Projects"/>
	                    	</g:link>
	                    </span>
	                    </g:if>
	                </g:form>
				</div>
	            
	        </div>
        </div>
    </body>
</html>
