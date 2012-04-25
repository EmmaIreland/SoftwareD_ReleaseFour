package survey

class QuestionController extends ControllerAssist{

	static allowedMethods = [save: post, update: post, delete: post]

	def index = {
		redirect(action: listString, params: params)
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
			flash.message = makeMessage(defaultNotFoundMessage, params.id)
			redirect(action: listString)
		}
	}

	def edit = {
		def questionInstance = Question.get(params.id)
		if (questionInstance) {
			return [questionInstance: questionInstance]
		}
		else {
			flash.message = makeMessage(defaultNotFoundMessage, params.id)
			redirect(action: listString)
		}
	}

	def update = {
		def questionInstance = Question.get(params.id)
		if (questionInstance) {
			if (params.version) {
				versionCheck(questionInstance, params.version, questionLabelString, 'Question')
			}
			questionInstance.properties = params
			if (!questionInstance.hasErrors() && questionInstance.save(flush)) {
				flash.message = makeMessage('default.updated.message', questionInstance.id)
				redirect(action: showString, id: questionInstance.id)
			}
			else {
				render(view: editString, model: [questionInstance: questionInstance])
			}
		}
		else {
			flash.message = makeMessage(defaultNotFoundMessage, params.id)
			redirect(action: listString)
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
			questionInstance.delete(flush)
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
