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
		            <h1>
		            	Hello
		            	<g:link controller="person" action="show" id="${person.id}">
		            		${person.name}</g:link>!
		            </h1>
		            <p>Welcome to the TwentyFourEyes' Survey Tool!</p>
	            </div>
	
				<g:if test="${!person.enrollments.isEmpty()}">
					<g:render template="myCourseBlock"
							  model="['header':'My Enrollments',
							  		  'courseList':(person.enrollments.sort { it.course.name }).collect({it.course})]" />
					<trinkets:emptyButtonsBar />
				</g:if>
				
				<g:if test="${!person.ownedCourses.isEmpty()}">
					<g:render template="myCourseBlock"
							  model="['header':'My Courses',
							  		  'courseList':person.ownedCourses.sort { it.name }]" />
					<div class="buttons">
						<span class="button">
	                   		<g:link class="add" controller="course" action="create">
	                   			New Course
	                  		</g:link>
	                   	</span>
						<span class="button"><input type="button" class="fakeButton"/></span>
					</div>
				</g:if>
				
				<g:set var="incompleteSurveys" value="${person.surveyAssignments.grep({!it.completed})}" />
				<g:if test="${!incompleteSurveys.isEmpty()}">
					<h2>Assigned Surveys</h2>
					<div class="dialog non-table-dialog">
						<div class="dialog-subDiv">
							<g:each in="${incompleteSurveys.collect({it.survey}).sort {it.title}}" status="i" var="survey">
								<g:link controller="survey" action="take" id="${survey.id}" >
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
				
				<g:set var="completedSurveys" value="${person.reports.sort {it.dateTaken}}" />
				<g:if test="${!completedSurveys.isEmpty()}">
					<h2>Completed Surveys</h2>
					<div class="dialog non-table-dialog">
						<div class="dialog-subDiv">
							<g:each in="${completedSurveys}" status="i" var="report">
								<g:link controller="report" action="show" id="${report.id}" >
									${report.survey.title}
								</g:link>
								<g:if test="${i != completedSurveys.size() - 1}">
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
