package survey

class ReportController extends ControllerAssist {

	static allowedMethods = [save: POST, update: POST, delete: POST]

	def index = {
		redirect(action: LIST, params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[reportInstanceList: Report.list(params), reportInstanceTotal: Report.count()]
	}

	def create = {
		def reportInstance = new Report()
		reportInstance.properties = params
		return [reportInstance: reportInstance]
	}

	def save = {
		def reportInstance = new Report(params)
		if (reportInstance.save(FLUSH)) {
			flash.message =  makeMessage('default.created.message', reportInstance.id)
			redirect(action: SHOW, id: reportInstance.id)
		}
		else {
			render(view: CREATE, model: [reportInstance: reportInstance])
		}
	}

	def show = {
		def reportInstance = Report.get(params.id)
		if (reportInstance) {
			[reportInstance: reportInstance]
		}
		else {
			flash.message =  makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
		}
	}

	def delete = {
		def reportInstance = Report.get(params.id)
		def reportSurvey = reportInstance.survey
		if (reportInstance) {
			try {
				reportInstance.delete(FLUSH)
				redirect(controller:'survey', action: SHOW, id:reportSurvey.id)
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message =  makeMessage('default.not.deleted.message', params.id)
				redirect(action: SHOW, id: params.id)
			}
		}
		else {
			flash.message =  makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
		}
	}

	private makeMessage(code, reportId) {
		return "${message(code: code, args: [reportLabel(), reportId])}"
	}

	private reportLabel() {
		message(code: REPORT_LABEL, default: 'Report')
	}
}