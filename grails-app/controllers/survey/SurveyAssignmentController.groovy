package survey

class SurveyAssignmentController {
	static post = 'POST'
	def surveyAssignmentLabel = 'surveyAssignment.label'
	def listString = 'list'
	def editString = 'edit'
	def createString = 'create'
	def showString = 'show'
	def defaultNotFoundMessage = 'default.not.found.message'
	def listMap = [action: listString]
	def flush = [flush: true]
	static allowedMethods = [save: post, update: post, delete: post]
	def index = {
		redirect(action: listString, params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[surveyAssignmentInstanceList: SurveyAssignment.list(params), surveyAssignmentInstanceTotal: SurveyAssignment.count()]
	}

	def create = {
		def surveyAssignmentInstance = new SurveyAssignment()
		surveyAssignmentInstance.properties = params
		[surveyAssignmentInstance: surveyAssignmentInstance, surveyid: params.surveyid]
	}

	def save = {
		def surveyAssignmentInstance = new SurveyAssignment(params)
		if (surveyAssignmentInstance.save(flush)) {
			flash.message = makeMessage('default.created.message', surveyAssignmentInstance.id)
			redirect(action: showString, id: surveyAssignmentInstance.id)
		}
		else {
			render(view: createString, model: [surveyAssignmentInstance: surveyAssignmentInstance])
		}
	}
	def show = {
		def surveyAssignmentInstance = SurveyAssignment.get(params.id)
		if (surveyAssignmentInstance) {
			[surveyAssignmentInstance: surveyAssignmentInstance]
		}
		else {
			flash.message = makeMessage(defaultNotFoundMessage, params.id)
			redirect(action: listString)
		}
	}

	def assign = {
		def students = params.list('student')

		students.each { student ->
			def tempSurveyAssignment = new SurveyAssignment(survey: Survey.get(params.surveyid),
				 person: Person.get(student))
			tempSurveyAssignment.save(flush)
		}

		redirect(controller: 'survey', action: showString, id: params.surveyid)
	}


	def edit = {
		def surveyAssignmentInstance = SurveyAssignment.get(params.id)
		if (surveyAssignmentInstance) {
			return [surveyAssignmentInstance: surveyAssignmentInstance]
		}
		else {
			flash.message = makeMessage(defaultNotFoundMessage, params.id)
			redirect(action: listString)
		}
	}

	def update = {
		def surveyAssignmentInstance = SurveyAssignment.get(params.id)
		if (surveyAssignmentInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (surveyAssignmentInstance.version > version) {
					surveyAssignmentInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
							[
								message(code: surveyAssignmentLabel, default: 'SurveyAssignment')]
							as Object[],
							'Another user has updated this SurveyAssignment while you were editing')
					render(view: editString, model: [surveyAssignmentInstance: surveyAssignmentInstance])
					return
				}
			}
			surveyAssignmentInstance.properties = params
			if (!surveyAssignmentInstance.hasErrors() && surveyAssignmentInstance.save(flush)) {
				flash.message = makeMessage('default.updated.message', surveyAssignmentInstance.id)
				redirect(action: showString, id: surveyAssignmentInstance.id)
			}
			else {
				render(view: editString, model: [surveyAssignmentInstance: surveyAssignmentInstance])
			}
		}
		else {
			flash.message = makeMessage(defaultNotFoundMessage, params.id)
			redirect(action: listString)
		}
	}

	def delete = {
		def surveyAssignmentInstance = SurveyAssignment.get(params.id)
		if (surveyAssignmentInstance) {
			try {
				surveyAssignmentInstance.delete(flush)
				flash.message = makeMessage('default.deleted.message', params.id)
				redirect(action: listString)
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = makeMessage('default.not.deleted.message', params.id)
				redirect(action: showString, id: params.id)
			}
		}
		else {
			flash.message = makeMessage(defaultNotFoundMessage, params.id)
			redirect(action: listString)
		}
	}

	private makeMessage(code, instanceId) {
		"${message(code: code, args: [label, instanceId])}"
	}

	private getLabel() {
		message(code: surveyAssignmentLabel, default: '')
	}
}
