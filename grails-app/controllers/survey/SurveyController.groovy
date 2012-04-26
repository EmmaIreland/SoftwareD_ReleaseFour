package survey
import survey.answers.*
import survey.questions.*
import grails.converters.JSON

class SurveyController extends ControllerAssist {

    static allowedMethods = [save: POST, update: POST, delete: POST, preview: POST]

    def index = {
        redirect(action: LIST, params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [surveyInstanceList: Survey.list(params), surveyInstanceTotal: Survey.count()]
    }

    def create = {
        def surveyInstance = new Survey()
        surveyInstance.properties = params
        return [surveyInstance: surveyInstance]
    }

    def save = {
        def surveyInstance = new Survey(params)
        if (params.existingSurvey != 'new') {
            def oldSurvey = Survey.get(params.existingSurvey)
            surveyInstance.questions = []

            oldSurvey.questions.each { question ->
                surveyInstance.questions.add(question.copyQuestion())
            }
        }

        if (surveyInstance.save(FLUSH)) {
            flash.message = makeMessage('default.created.message', surveyInstance.title)
            redirect(action: SHOW, id: surveyInstance.id)
        }
        else {
            render(view: CREATE, model: [surveyInstance: surveyInstance])
        }
    }

    private removeEmptyElements(def input) {
        def list = input as List
        list.retainAll{
            it.length() > 0
        }
        return list
    }

    def addQuestion = {
        def surveyInstance = Survey.get(params.surveyid)
        def surveyType = params.type
        def questionInstance

        switch(surveyType){
            case 'existing':
                questionInstance = Question.get(params.questionid).copyQuestion()
                break
            case 'checkbox':
                def choices = removeEmptyElements(params.cbChoices)
                questionInstance = new CheckboxQuestion(prompt: params.cbPrompt,
                        choices: choices).save(FAIL_ON_ERROR)
                break
            case 'multipleChoice':
                def choices = removeEmptyElements(params.mcChoices)
                questionInstance = new MultipleChoiceQuestion(prompt: params.mcPrompt,
                        choices: choices).save(FAIL_ON_ERROR)
                break
            case 'shortResponse':
                questionInstance = new ShortTextQuestion(prompt: params.stPrompt).save(FAIL_ON_ERROR)
                break
            case 'longResponse':
                questionInstance = new LongTextQuestion(prompt: params.ltPrompt).save(FAIL_ON_ERROR)
                break
        }
        surveyInstance.addToQuestions(questionInstance)
        surveyInstance.save(FAIL_ON_ERROR)
        render questionInstance as JSON
    }

    def show = {
        def surveyInstance = Survey.get(params.id)
        def existingQuestions = Question.findAll()
        if (surveyInstance) {
            [surveyInstance: surveyInstance, existingQuestions: existingQuestions]
        }
        else {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
            redirect(action: LIST)
        }
    }

    def preview = {
        def surveyInstance = Survey.get(params.id)
        if (surveyInstance) {
            [surveyInstance: surveyInstance]
        }
        else {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
            redirect(action: LIST)
        }
    }

    def submit = {
        def surveyInstance = Survey.get(params.id)
        def personInstance = Person.get(params.personid)
	def surveyReport = new Report(person: personInstance, survey: surveyInstance, answers: []).save(FAIL_ON_ERROR)
        if (!surveyInstance || !personInstance) {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
            redirect(action: LIST)
            return
        }
        def questions = surveyInstance.questions

        questions.each { question ->
            def serverResponse = params[question.id.toString()]
            if (serverResponse == null) {
                surveyReport.answers.add(createAnswer(question, personInstance, [], surveyReport))
            } else {
                surveyReport.answers.add(createAnswer(question, personInstance, serverResponse, surveyReport))
            }
        }

	surveyReport.save(FAIL_ON_ERROR)
        // not necessary with each once SurveyAssignment is unique
        SurveyAssignment.findBySurveyAndPerson(surveyInstance, personInstance).each { it.completed = true }
	redirect(controller: 'report', action: SHOW, id:surveyReport.id)
        //redirect(controller: 'person', action: SHOW, id:personInstance.id)
    }

    private createAnswer(question, person, serverResponse, surveyReport) {
        def answer
        switch(question.templateName) {
            case 'Long':
            case 'Short':
                answer = new TextAnswer(response: serverResponse,
                person: person, question: question, report: surveyReport).save(FAIL_ON_ERROR)
                break
            case 'MultipleChoice':
                answer = new MultipleChoiceAnswer(responseIndex: serverResponse,
                person: person, question: question, report: surveyReport).save(FAIL_ON_ERROR)
                break
            case 'Checkbox':
                def responseMap = [:]
                if (serverResponse instanceof String) {
                    serverResponse = [serverResponse]
                } else {
                    serverResponse = serverResponse as List
                }
		
                question.choices.eachWithIndex { choice, index ->
		    
                    responseMap[index.toString()] = serverResponse.contains(index)
		    
                }
                answer = new CheckboxAnswer(responses: responseMap, person: person, question: question, report: surveyReport)
                answer.save(FAIL_ON_ERROR)
                break
            default:
                break
        }
	answer
    }


    def take = {
        def surveyInstance = Survey.get(params.id)
        def personInstance = Person.get(session['user'])
        if (!surveyInstance || !personInstance) {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
            redirect(action: LIST)
        }
        else {
            [surveyInstance: surveyInstance, personInstance: personInstance]
        }
    }

    def edit = {
        def surveyInstance = Survey.get(params.id)
        if (surveyInstance) {
            return [surveyInstance: surveyInstance]
        }
        else {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
            redirect(action: LIST)
        }
    }

    def update = {
        def surveyInstance = Survey.get(params.id)
        if (surveyInstance) {
            if (params.version) {
                versionCheck(surveyInstance, params.version, SURVEY_LABEL, 'Survey')
            }
            surveyInstance.properties = params
            if (!surveyInstance.hasErrors() && surveyInstance.save(FLUSH)) {
                flash.message = makeMessage('default.updated.message', surveyInstance.title)
                redirect(action: SHOW, id: surveyInstance.id)
            }
            else {
                render(view: EDIT, model: [surveyInstance: surveyInstance])
            }
        }
        else {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
            redirect(action: LIST)
        }
    }

    def delete = {
        def surveyInstance = Survey.get(params.id)
	def surveyProject = surveyInstance.project
	surveyInstance.surveyAssignments.each { assignment -> assignment.delete() }
        if (surveyInstance) {
            try {
                surveyInstance.delete(FLUSH)
                flash.message = makeMessage('default.deleted.message', surveyInstance.title)
                redirect(controller: 'project', action: SHOW, id:surveyProject.id)
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

    private makeMessage(code, surveyId) {
        return "${message(code: code, args: [surveyLabel(), surveyId])}"
    }

    private surveyLabel() {
        message(code: SURVEY_LABEL, default: 'Survey')
    }
}
