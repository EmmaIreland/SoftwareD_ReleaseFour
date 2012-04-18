<%@ page import="survey.Person" %>
<html>
    <head>
        <title>TwentyFourEyes</title>
        <meta name="layout" content="main" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'home.css')}" />
    </head>
    <body>
 
        <div class="body">
			<g:set var="person" value="${Person.get(session['user'])}" />
            
            <g:if test="${person}">
	            <div id="welcome-message">
		            <h1>Hello ${person.name}!</h1>
		            <p>Welcome to the TwentyFourEyes' Survey Tool!</p>
	            </div>
	
				<g:if test="${!person.enrollments.isEmpty()}">
					<g:render template="myCourseBlock"
							  model="['header':'My Enrollments',
							  		  'courseList':(person.enrollments.sort { it.course.name }).collect({it.course})]" />
				</g:if>
				
				<g:if test="${!person.ownedCourses.isEmpty()}">
					<g:render template="myCourseBlock"
							  model="['header':'My Courses',
							  		  'courseList':person.ownedCourses.sort { it.name }]" />
				</g:if>
			</g:if>
		</div>
    </body>
</html>
