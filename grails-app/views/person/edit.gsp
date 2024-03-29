<%@ page import="survey.Person"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'person.label', default: 'Person')}" />
<title><g:message code="default.edit.label" args="[entityName]" />
</title>
</head>
<body>
	<div class="nav">
		<span class="menuButton">
			<a class="home" href="${createLink(uri: '/')}">
				<g:message code="default.home.label" />
			</a>
		</span>
		<g:if test="${isAdmin}">
			<span class="menuButton">
				<g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link>
			</span>
			<span class="menuButton">
				<g:link class="create" action="create">	<g:message code="default.new.label" args="[entityName]" /></g:link>
			</span>
		</g:if>
	</div>
	<div class="body">
		<h1>
			<g:message code="default.edit.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:hasErrors bean="${personInstance}">
			<div class="errors">
				<g:renderErrors bean="${personInstance}" as="list" />
			</div>
		</g:hasErrors>
		<g:form method="post">
			<g:hiddenField name="id" value="${personInstance?.id}" />
			<g:hiddenField name="version" value="${personInstance?.version}" />
			<div class="dialog">
				<table>
					<tbody>

						<tr class="prop">
							<td valign="top" class="name">
								<label for="name">
									<g:message code="person.name.label" default="Name" />
								</label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean: personInstance, field: 'name', 'errors')}">
								<g:textField name="name" value="${personInstance?.name}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name">
								<label for="email">
									<g:message code="person.email.label" default="Email" />
								</label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean: personInstance, field: 'email', 'errors')}">
								<g:textField name="email" value="${personInstance?.email}" />
							</td>
						</tr>

					</tbody>
				</table>
			</div>
			<div class="buttons">
				<span class="button">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
				</span>
				<g:if test="${isAdmin}" }>
					<span class="button">
						<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"
										onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</span>
				</g:if>
			</div>
		</g:form>
	</div>
</body>
</html>
