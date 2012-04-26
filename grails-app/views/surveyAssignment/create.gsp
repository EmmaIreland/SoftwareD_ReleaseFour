
<%@ page import="survey.Survey" %>
<%@ page import="survey.SurveyAssignment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'surveyAssignment.label', default: 'SurveyAssignment')}" />
        <title>Assign Survey</title>
        <g:javascript src="assignsurvey.js" />
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
            	<input type=button name="checkAll" value="Select all" id="checkAll">
                <input type=button name="uncheckAll" value="Deselect all" id="uncheckAll">
                <div class="dialog">
					
						<g:each in="${Survey.get(surveyid).getTeamsForAssignment()}" var="team">
							<h2><input type="checkbox" name="group" value="${team.id}" class="teamCheckbox" /> ${team.name}</h2>
							<div id="${team.id}" class="smallIndent">
								<g:each in="${team.memberships}" var="membership">
									<input type="checkbox"
										   name="student"
										   value="${membership.member.id}"
										   <g:if test="${membership.member.surveyAssignments*.survey.contains(Survey.get(surveyid))}">
										   		checked="checked"
										   		disabled="disabled"
										   </g:if>
										   />
									<g:link controller="person" action="show" id="${membership.member.id}">
										${membership.member?.encodeAsHTML()}
									</g:link>
									<br/>
								</g:each>
							</div>
						</g:each>
					</div>
                <div class="buttons">
                    <span class="button">
                    <g:hiddenField name="surveyid" value="${surveyid}"/>
                    <g:submitButton name="create" class="assign" action="assign" params="${['surveyid': surveyid]}" value="${message(code: 'default.button.assign.label', default: 'Assign')}" />
                    </span>
                </div>
            </g:form>
        </div>
    </body>
</html>
