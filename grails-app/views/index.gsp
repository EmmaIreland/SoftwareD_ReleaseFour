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
				
				<g:set var="incompleteSurveys" value="${person.surveyAssignments.grep({!it.completed})}" />
				<g:if test="${!incompleteSurveys.isEmpty()}">
					<h2>Assigned Surveys</h2>
					<div class="dialog non-table-dialog">
						<div class="dialog-subDiv">
							<g:each in="${incompleteSurveys.collect({it.survey}).sort {it.title}}" status="i" var="survey">
								<g:link controller="survey" action="take" id="${survey.id}" params="['personid':session['user']]" >
									${survey.title}
								</g:link>
								<g:if test="${i != incompleteSurveys.size() - 1}">
									<br/>
								</g:if>
							</g:each>
						</div>
					</div>
					<trinkets:emptyButtonsBar />
				</g:if>
			</g:if>
		</div>
    </body>
</html>
