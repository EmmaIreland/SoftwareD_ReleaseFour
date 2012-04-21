<g:if test="${!session['user']}">
	<g:redirectToLogin currentURL="${request.forwardURI}" />
</g:if>
<%@ page import="survey.Person"%>
<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="TwentyFourEyes" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'trinkets.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.png')}" type="image/x-icon" />
        <r:require module="jquery"/>
        <r:require module="jquery-ui"/>
        <r:use module="jquery-validate"/>
        <r:layoutResources/>
        <g:javascript src="trinkets.js" />
        <r:layoutResources disposition="defer"/>
        <g:layoutHead />
    </head>
    <body>
        <div id="sideBarNav">
        	<div><a href="${createLink(uri: '/')}"><img id="appLogo" src="${resource(dir:'images',file:'24eyes.png')}" alt="Grails" border="0" /></a></div>
        	<br>
        	<div id="appName"><a href="${createLink(uri: '/')}">TwentyFourEyes</a></div>
        	<div id="controllers">
        		<div id="title">View</div>
        		<div id="list">
        			<ul>
	                    <li class="controller"><g:link controller="course">Courses</g:link></li>
	                    <li class="controller"><g:link controller="project">Projects</g:link></li>
	                    <li class="controller"><g:link controller="person">People</g:link></li>
	                    <li class="controller"><g:link controller="survey">Surveys</g:link></li>
                    </ul>
                </div>
        	</div>
       		<g:if test="${session['user']}">
	        	<div id="login">
        			<g:set var='loggedInUser' value="${Person.get(session['user'])}" />
        			You are logged in as:
        			<br/>
        			<g:link controller='person' action='show' id="${session['user']}">
        				${fieldValue(bean: loggedInUser, field: "name")}
        			</g:link>
       				<g:if test="${loggedInUser.isAdmin}">
       					<br/>(Admin)
       				</g:if>
        			<br/><br/>
        			<g:link controller='person' action='logout'>Logout</g:link>
	        	</div>
       		</g:if>
        </div>
        <div style="float: left">
        	<g:layoutBody />
        </div>
    </body>
</html>