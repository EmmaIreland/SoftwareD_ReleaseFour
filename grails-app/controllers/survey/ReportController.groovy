package survey

class ReportController extends ControllerAssist {

    static allowedMethods = [save: post, update: post, delete: post]

    def index = {
        redirect(action: listString, params: params)
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
        if (reportInstance.save(flush)) {
			flash.message =  makeMessage('default.created.message', reportInstance.id)
			redirect(action: showString, id: reportInstance.id)
        }
        else {
            render(view: createString, model: [reportInstance: reportInstance])
        }
    }

    def show = {
        def reportInstance = Report.get(params.id)
        if (reportInstance) {
			[reportInstance: reportInstance]
        }
        else {
			flash.message =  makeMessage(defaultNotFoundMessage, params.id)
			redirect(action: listString)
        }
    }
    
    def delete = {
        def reportInstance = Report.get(params.id)
        if (reportInstance) {
            try {
                reportInstance.delete(flush)
				flash.message =  makeMessage('default.deleted.message', params.id)
                redirect(action: listString)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message =  makeMessage('default.not.deleted.message', params.id)
                redirect(action: showString, id: params.id)
            }
        }
        else {
			flash.message =  makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
    }
	
	private makeMessage(code, reportId) {
		return "${message(code: code, args: [reportLabel(), reportId])}"
	}

	private reportLabel() {
		message(code: 'report.label', default: 'Report')
	}
}