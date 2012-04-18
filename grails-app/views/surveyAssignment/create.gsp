
<%@ page import="survey.Survey" %>
<%@ page import="survey.SurveyAssignment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'surveyAssignment.label', default: 'SurveyAssignment')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <g:javascript>
        function checkAll(field)
		{
		for (i = 0; i < field.length; i++)
			field[i].checked = true ;
		}
		
		function uncheckAll(field)
		{
		for (i = 0; i < field.length; i++)
			field[i].checked = false ;
		}
        </g:javascript>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>Assign Survey</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${surveyAssignmentInstance}">
            <div class="errors">
                <g:renderErrors bean="${surveyAssignmentInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form name="saveform" action="assign" >
            	<input type=button name="CheckAll" value="Assign to all" onclick="checkAll(document.saveform.student)">
                <input type=button name="UnCheckAll" value="Unassign to all" onclick="uncheckAll(document.saveform.student)">
                <div class="dialog">
                    <table>
                        <tbody>
                        
                     <div class="demo">
					
						<g:each in="${Survey.get(surveyid).project.teams.sort{it.name}}" var="team">
							<trinkets:collapsibleDiv title="${team.name}">
							<div id="${team.id}">
								<h2>Members:</h2>
								<g:each in="${team.memberships.member}" var="student">
									<input type="checkbox" name="student" value="${student}" />
									<g:link controller="person" action="show" id="${student}">${student?.encodeAsHTML()}</g:link>
									<br>
								</g:each>
							</div>
							</trinkets:collapsibleDiv>
						</g:each>
					</div>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" action="assign" params="${['surveyid': surveyid]}" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
