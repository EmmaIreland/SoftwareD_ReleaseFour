<h2>${header}</h2>
<div class="dialog non-table-dialog">
	<div class="dialog-subDiv">
		<g:each in="${courseList}" status="i" var="course">
			<g:link controller="course" action="show" id="${course.id}">
				${course.abbreviation + ': ' + course.name}
			</g:link>
			<g:if test="${i != courseList.size() - 1}">
				<br/>
			</g:if>
		</g:each>
	</div>
</div>