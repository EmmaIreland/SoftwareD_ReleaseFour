<%@ page import="survey.Person" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'personlist.css')}" />
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
	        	<g:each in="${personInstanceList}" status="i" var="personInstance">
                	<trinkets:collapsibleDiv title="${personInstance.name}">
                       
                        <div id="${personInstance.id}">
                        	<g:link controller="person" action="show" id="${personInstance.id}">
                        	${personInstance.name}'s home page 
                        	</g:link>
                        </div>
                        
						<div id="email${personInstance.id}">
							<h4>Email: <a href="mailto:${personInstance.email}">${personInstance.email}</a> </h4>
						</div>
   
						<div id="${personInstance.enrollments.course}">
							<h4>Enrollments</h4>
							<g:each in="${personInstance.enrollments.course}" var="e">
								<ul>
									<li>
										<g:link controller="course" action="show" id="${e.id}">
											${e?.encodeAsHTML()}
										</g:link>
									</li>
								</ul>
							</g:each>
						</div>

						<div id="${personInstance.memberships.team}">
							<h4>Memberships</h4>
							<g:each in="${personInstance.memberships.team}" var="m">
								<ul>
									<li>
										<g:link controller="project" action="show" id="${m.project.id}">
											${m?.encodeAsHTML()}
										</g:link>
									</li>
								</ul>
							</g:each>
						</div>

						<div id="${personInstance.ownedCourses}">
							<h4>Owned Courses</h4>
							<g:each in="${personInstance.ownedCourses}" var="o">
								<ul>
									<li>
										<g:link controller="course" action="show" id="${o.id}">
											${o?.encodeAsHTML()}
										</g:link>
									</li>
								</ul>
							</g:each>
						</div>
                                
                	</trinkets:collapsibleDiv>
            	</g:each>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${personInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
