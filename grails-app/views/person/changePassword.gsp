<%@ page import="survey.Person"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName" value="Change Password" />
<title><g:message code="default.edit.label" args="[entityName]" />
</title>

<script type="text/javascript">
$().ready(function() {


	// validate signup form on keyup and submit
	$("#signupForm").validate({
		 errorPlacement: function(error, element) {
			 error.appendTo( element.next("div") );
		},
		rules: {
			old_password: {
				required: true
			},
			password: {
				required: true,
				minlength: 5,

			},
			confirm_password: {
				required: true,
				minlength: 5,
				equalTo: "#password"
			},
		},
		messages: {
			old_password: {
				required: "Enter your current password"
			},
			password: {
				required: "Enter a new password",
				minlength: "Your password must be at least 5 characters long"
			},
			confirm_password: {
				required: "Re-Enter your new password.",
				minlength: "Your password must be at least 5 characters long",
				equalTo: "Please enter the same password as above"
			},
		}
	});
});
</script>
</head>
<body>
	<div class="nav">
		<span class="menuButton"> <a class="home"
			href="${createLink(uri: '/')}"> <g:message
					code="default.home.label" /> </a> </span>
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

		<form class="cmxform" id="signupForm" method="post" action="">
			<g:hiddenField name="id" value="${personInstance?.id}" />
			<g:hiddenField name="version" value="${personInstance?.version}" />
			<g:hiddenField name="name" value="${personInstance?.name}" />
			<g:hiddenField name="email" value="${personInstance?.email}" />
			<div class="dialog">
								<div></div>
				<table>
					<tbody>

						<tr class="prop">
							<td valign="top" class="name"><label for="password">
									<g:message code="person.password.label"
										default="Enter Current Password" /></label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean: personInstance, field: 'password', 'errors')}">
								<g:passwordField name="old_password" value="${''}" />
								<div></div>
							</td>
						</tr>
						<tr class="prop">
							<td valign="top" class="name"><label for="password">
									<g:message code="person.password.label"
										default="Enter New Password" /> </label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean: personInstance, field: 'password')}">
								<g:passwordField name="password" value="${''}" />
								<div></div>
							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="password">
									<g:message code="person.password.label"
										default="Re-Enter New Password" /> </label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean: personInstance, field: 'password')}">
								<g:passwordField name="confirm_password" value="${''}" />
								<div></div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="buttons">

				<span class="button"><g:actionSubmit class="save"
						action="updatePassword"
						value="${message(code: 'default.buttton.update.label', default: 'Update')}" />
				</span> 
			</div>
		</form>
	</div>
</body>
</html>
