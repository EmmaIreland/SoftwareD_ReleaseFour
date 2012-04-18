package survey

class SurveyAssignmentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
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
        if (surveyAssignmentInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'surveyAssignment.label', default: 'SurveyAssignment'), surveyAssignmentInstance.id])}"
            redirect(action: "show", id: surveyAssignmentInstance.id)
        }
        else {
            render(view: "create", model: [surveyAssignmentInstance: surveyAssignmentInstance])
        }
    }

    def show = {
        def surveyAssignmentInstance = SurveyAssignment.get(params.id)
        if (!surveyAssignmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyAssignment.label', default: 'SurveyAssignment'), params.id])}"
            redirect(action: "list")
        }
        else {
            [surveyAssignmentInstance: surveyAssignmentInstance]
        }
    }
    
    def assign = {
	def students = params.list('student')
	println "the list of students = " + students
	println "surveyid is " + params.surveyid
	println "Survey is " + Survey.get(params.surveyid)
	
	students.each { person ->
	    def surveyAssignment = new SurveyAssignment(survey: Survey.get(params.surveyid), person: person).save(flush:true)
	}
	redirect(action: "show", id: params.surveyid)
    }

    def edit = {
        def surveyAssignmentInstance = SurveyAssignment.get(params.id)
        if (!surveyAssignmentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyAssignment.label', default: 'SurveyAssignment'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [surveyAssignmentInstance: surveyAssignmentInstance]
        }
    }

    def update = {
        def surveyAssignmentInstance = SurveyAssignment.get(params.id)
        if (surveyAssignmentInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (surveyAssignmentInstance.version > version) {
                    
                    surveyAssignmentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'surveyAssignment.label', default: 'SurveyAssignment')] as Object[], "Another user has updated this SurveyAssignment while you were editing")
                    render(view: "edit", model: [surveyAssignmentInstance: surveyAssignmentInstance])
                    return
                }
            }
            surveyAssignmentInstance.properties = params
            if (!surveyAssignmentInstance.hasErrors() && surveyAssignmentInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'surveyAssignment.label', default: 'SurveyAssignment'), surveyAssignmentInstance.id])}"
                redirect(action: "show", id: surveyAssignmentInstance.id)
            }
            else {
                render(view: "edit", model: [surveyAssignmentInstance: surveyAssignmentInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyAssignment.label', default: 'SurveyAssignment'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def surveyAssignmentInstance = SurveyAssignment.get(params.id)
        if (surveyAssignmentInstance) {
            try {
                surveyAssignmentInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'surveyAssignment.label', default: 'SurveyAssignment'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'surveyAssignment.label', default: 'SurveyAssignment'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'surveyAssignment.label', default: 'SurveyAssignment'), params.id])}"
            redirect(action: "list")
        }
    }
}
