
<%@ page import="survey.Report" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'report.label', default: 'Report')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>Responses to ${reportInstance.survey.title}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="report.person.label" default="Survey Taker" /></td>
                            
                            <td valign="top" class="value"><g:link controller="person" action="show" id="${reportInstance?.person?.id}">${reportInstance?.person?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="report.survey.label" default="Survey" /></td>
                            
                            <td valign="top" class="value"><g:link controller="survey" action="show" id="${reportInstance?.survey?.id}">${reportInstance?.survey?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="survey.dueDate.label" default="Due Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${reportInstance?.survey?.dueDate}" format="MMMMM d, yyyy, h:m a" /></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="report.dateTaken.label" default="Date Taken" /></td>
                            
                            <td valign="top" class="value">
                            	<g:formatDate date="${reportInstance?.dateTaken}" format="MMMMM d, yyyy, h:m a" />
                            	<g:if test="${reportInstance.isOverdue()}">
                            		<span style="font-weight: bold; color: red">(Overdue)</span>
                            	</g:if>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="report.answers.label" default="Responses" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${reportInstance.answers}" var="a">
                                    <li>
                                    	"${a.question.prompt}"
                                        <br>
                                        Answer given: <span style="font-weight: bold">${a?.encodeAsHTML()}</span>
                                    </li>
                                    <br>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${reportInstance?.id}" />
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
