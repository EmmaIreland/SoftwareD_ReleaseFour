
<%@ page import="survey.SurveyAssignment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'surveyAssignment.label', default: 'SurveyAssignment')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'surveyAssignment.id.label', default: 'Id')}" />
                        
                            <th><g:message code="surveyAssignment.survey.label" default="Survey" /></th>
                        
                            <th><g:message code="surveyAssignment.person.label" default="Person" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${surveyAssignmentInstanceList}" status="i" var="surveyAssignmentInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${surveyAssignmentInstance.id}">${fieldValue(bean: surveyAssignmentInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: surveyAssignmentInstance, field: "survey")}</td>
                        
                            <td>${fieldValue(bean: surveyAssignmentInstance, field: "person")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${surveyAssignmentInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
