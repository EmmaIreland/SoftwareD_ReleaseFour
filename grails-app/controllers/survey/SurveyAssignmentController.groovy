package survey

class SurveyAssignmentController extends ControllerAssist {
	
	static allowedMethods = [save: POST, update: POST, delete: POST]
	
	def index = {
		redirect(action: LIST, params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[surveyAssignmentInstanceList: SurveyAssignment.list(params), surveyAssignmentInstanceTotal: SurveyAssignment.count()]
	}

	def create = {
            if ( params.surveyid ) {
		def surveyAssignmentInstance = new SurveyAssignment()
		surveyAssignmentInstance.properties = params
		[surveyAssignmentInstance: surveyAssignmentInstance, surveyid: params.surveyid]
            } else {
                redirect(uri: '/404')
            }
	}

	def save = {
		def surveyAssignmentInstance = new SurveyAssignment(params)
		if (surveyAssignmentInstance.save(FLUSH)) {
			flash.message = makeMessage('default.created.message', surveyAssignmentInstance.id)
			redirect(action: SHOW, id: surveyAssignmentInstance.id)
		}
		else {
			render(view: CREATE, model: [surveyAssignmentInstance: surveyAssignmentInstance])
		}
	}
	def show = {
		def surveyAssignmentInstance = SurveyAssignment.get(params.id)
		if (surveyAssignmentInstance) {
			[surveyAssignmentInstance: surveyAssignmentInstance]
		}
		else {
			flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
		}
	}

	def assign = {
		def students = params.list('student')

		students.each { student ->
			def tempSurveyAssignment = new SurveyAssignment(survey: Survey.get(params.surveyid),
				 person: Person.get(student))
			tempSurveyAssignment.save(FLUSH)
		}

		redirect(controller: 'survey', action: SHOW, id: params.surveyid)
	}


	def edit = {
		def surveyAssignmentInstance = SurveyAssignment.get(params.id)
		if (surveyAssignmentInstance) {
			return [surveyAssignmentInstance: surveyAssignmentInstance]
		}
		else {
			flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
		}
	}

	def update = {
		def surveyAssignmentInstance = SurveyAssignment.get(params.id)
		if (surveyAssignmentInstance) {
			if (params.version) {
				versionCheck(surveyAssignmentInstance, params.version, SURVEYASSIGNMENT_LABEL, 'SurveyAssignment')
			}
			surveyAssignmentInstance.properties = params
			if (!surveyAssignmentInstance.hasErrors() && surveyAssignmentInstance.save(FLUSH)) {
				flash.message = makeMessage('default.updated.message', surveyAssignmentInstance.id)
				redirect(action: SHOW, id: surveyAssignmentInstance.id)
			}
			else {
				render(view: EDIT, model: [surveyAssignmentInstance: surveyAssignmentInstance])
			}
		}
		else {
			flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
		}
	}

	def delete = {
		def surveyAssignmentInstance = SurveyAssignment.get(params.id)
		if (surveyAssignmentInstance) {
			try {
				surveyAssignmentInstance.delete(FLUSH)
				flash.message = makeMessage('default.deleted.message', params.id)
				redirect(action: LIST)
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = makeMessage('default.not.deleted.message', params.id)
				redirect(action: SHOW, id: params.id)
			}
		}
		else {
			flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
		}
	}

	private makeMessage(code, instanceId) {
		"${message(code: code, args: [label, instanceId])}"
	}

	private getLabel() {
		message(code: SURVEYASSIGNMENT_LABEL, default: '')
	}
}
