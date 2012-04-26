package survey

class ControllerAssist {

	//authentication strings
	static final NO_PERMISSION_URL = '/nopermission'
	static final COURSE_STRING = 'course'
	static final USER_STRING = 'user'
	static final PERSON_STRING = 'person'
	static final LOGIN_STRING = 'login'
	
	
	
	//maps
	static final FLUSH = [flush: true]
	static final FAIL_ON_ERROR = [failOnError: true]
	static final LIST_MAP = [action: LIST]

	//action strings
	static final CREATE = 'create'
	static final EDIT = 'edit'
	static final LIST = 'list'
	static final SHOW = 'show'

	//label strings
	static final PERSON_LABEL = 'person.label'
	static final PROJECT_LABEL = 'project.label'
	static final TEAM_LABEL = 'team.label'
	static final COURSE_LABEL = 'course.label'
	static final REPORT_LABEL = 'report.label'
	static final SURVEY_LABEL = 'survey.label'
	static final ENROLLMENT_LABEL = 'enrollment.label'
	static final QUESTION_LABEL = 'question.label'
	static final SURVEYASSIGNMENT_LABEL = 'surveyAssignment.label'
	

	//other strings
	static final POST = 'POST'
	static final DEFAULT_NOTFOUND_MESSAGE = 'default.not.found.message'

	// method for the version checking in update action
	def versionCheck(instance, paramsVersion, label, controllerString) {
		def version = paramsVersion.toLong()
		if (instance.version > version) {
			instance.errors.rejectValue('version', 'default.optimistic.locking.failure',
					[message(code: label, default: controllerString)] as Object[],
					'Another user has updated this while you were editing')
			render(view: EDIT, model: [instance: instance])
			return
		}
	}

}
