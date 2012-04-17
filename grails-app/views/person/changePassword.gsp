<%@ page import="survey.Person"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName"
	value="Change Password" />
<title><g:message code="default.edit.label" args="[entityName]" />
</title>
</head>
<body>
	<div class="nav">
		<span class="menuButton"> <a class="home"
			href="${createLink(uri: '/')}"> <g:message code="default.home.label" /> </a> </span> 
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
			<g:hiddenField name="name" value="${personInstance?.name}" />
			<g:hiddenField name="email" value="${personInstance?.email}" />
			<div class="dialog">
				<table>
					<tbody>

						<tr class="prop">
							<td valign="top" class="name"><label for="password">
									<g:message code="person.password.label" default="Enter Current Password" />
							</label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean: personInstance, field: 'password')}">
								<g:passwordField name="password"
									value="${''}" />
							</td>
						</tr>
						<tr class="prop">
							<td valign="top" class="name"><label for="password">
									<g:message code="person.password.label" default="Enter New Password" />
							</label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean: personInstance, field: 'password')}">
								<g:passwordField name="password"
									value="${''}" />
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="password">
									<g:message code="person.password.label"
										default="Re-Enter New Password" /> </label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean: personInstance, field: 'password', 'errors')}">
								<g:passwordField name="confirm_password"
									value="${''}" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="buttons">
				<span class="button"> <g:actionSubmit class="save"
						action="update"
						value="${message(code: 'default.button.update.label', default: 'Update')}" />
				</span> <span class="button"> <g:actionSubmit class="delete"
						action="delete"
						value="${message(code: 'default.button.delete.label', default: 'Delete')}"
						onclick="return confirm(
										'${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'
									);" />
				</span>
			</div>
		</g:form>
	</div>
</body>
</html>
