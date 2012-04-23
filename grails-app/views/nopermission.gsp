<%@ page import="survey.Person" %>
<html>
    <head>
        <title>Access Denied</title>
        <meta name="layout" content="main" />
    </head>
    <body>
 
        <div class="body">
			<g:set var="person" value="${Person.get(session['user'])}" />
            
            <g:if test="${person}">
	            <div id="welcome-message">
		            <h1>
		            	Access Denied
		            </h1>
		            <p style="font-size: 12px">
		            	You do not have permissions for the page that you tried to access.
		            	If you believe this is a mistake, please contact your system administrator.
		            </p>
		            <p style="font-size: 12px">
		            	In the meantime, please
		            	go <a href="" onclick="history.go(-1)">back</a>,
		            	go <g:link uri="/">home</g:link>
		            	or try another page.
		            </p>
	            </div>
			</g:if>
		</div>
    </body>
</html>
