package survey

class QuestionController extends ControllerAssist{

	static allowedMethods = [save: POST, update: POST, delete: POST]

	def index = {
		redirect(action: LIST, params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[questionInstanceList: Question.list(params), questionInstanceTotal: Question.count()]
	}

	def show = {
		def questionInstance = Question.get(params.id)
		if (questionInstance) {
			[questionInstance: questionInstance]
		}
		else {
			flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
		}
	}

	def edit = {
		def questionInstance = Question.get(params.id)
		if (questionInstance) {
			return [questionInstance: questionInstance]
		}
		else {
			flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
		}
	}

	def update = {
		def questionInstance = Question.get(params.id)
		if (questionInstance) {
			if (params.version) {
				versionCheck(questionInstance, params.version, QUESTION_LABEL, 'Question')
			}
			questionInstance.properties = params
			if (!questionInstance.hasErrors() && questionInstance.save(FLUSH)) {
				flash.message = makeMessage('default.updated.message', questionInstance.id)
				redirect(action: SHOW, id: questionInstance.id)
			}
			else {
				render(view: EDIT, model: [questionInstance: questionInstance])
			}
		}
		else {
			flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
		}
	}


	def delete = {
		def questionInstance = Question.get(params.id)
		if (questionInstance) {
			def allSurveys = Survey.findAll()

			for ( i in allSurveys ) {
				if (i.questions.contains(questionInstance)) {
					i.questions.remove(questionInstance)
					break
				}
			}
			questionInstance.delete(FLUSH)
		}
		render('Success.')
	}

	private makeMessage(code, questionId) {
		return "${message(code: code, args: [questionLabel(), questionId])}"
	}

	private questionLabel() {
		message(code: questionLabelString, default: 'Question')
	}
}
