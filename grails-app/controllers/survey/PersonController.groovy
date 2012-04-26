package survey

class PersonController extends ControllerAssist {
    def authenticationService

    static allowedMethods = [save: POST, update: POST, delete: POST]

    def index = {
        redirect(action: LIST, params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [personInstanceList: Person.list(params), personInstanceTotal: Person.count()]
    }

    def create = {
        def personInstance = new Person()
        personInstance.properties = params
        return [personInstance: personInstance]
    }

    def save = {
        def personInstance = new Person(params)
        if(params.password == params.confirm_password){
            if (personInstance.save(FLUSH)) {
                flash.message = makeMessage('default.created.message', personInstance.name)
                redirect(action: SHOW, id: personInstance.id)
            } else {
                render(view: CREATE, model: [personInstance: personInstance])
            }
        }
    }

    def show = {
        def personInstance = Person.get(params.id)
        if (personInstance) {
            [personInstance: personInstance]
        }
        else {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
            redirect(action: LIST)
        }
    }

    def edit = {
        def personInstance = Person.get(params.id)
        if (personInstance) {
            return [personInstance: personInstance]
        }
        else {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
            redirect(action: LIST)
        }
    }

    def update = {
        def personInstance = Person.get(params.id)
        if (personInstance) {
            if (params.version) {
				versionCheck(personInstance, params.version, PERSON_LABEL, 'Person')
            }
            personInstance.name = params.name
            personInstance.email = params.email
            if (!personInstance.hasErrors() && personInstance.save(FLUSH)) {
                flash.message = makeMessage('default.updated.message', personInstance.toString())
                redirect(action: SHOW, id: personInstance.id)
            }
            else {
                render(view: EDIT, model: [personInstance: personInstance])
            }
        }
        else {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
            redirect(action: LIST)
        }
    }

    def delete = {
        def personInstance = Person.get(params.id)
        if (personInstance) {
			if ( session['user'] == personInstance.id ) {
				authenticationService.logout()
			}
            try {
                personInstance.delete(FLUSH)
                flash.message = makeMessage('default.deleted.message', personInstance.toString())
                redirect(action: LIST)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = makeMessage('default.not.deleted.message', personInstance.toString())
                redirect(action: SHOW, id: params.id)
            }
        }
        else {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
            redirect(action: LIST)
        }
    }

    def sendLogin = {
        def email = params.email
        def password = params.password

        def person = authenticationService.validateLogin(email, password)
        if ( person ) {
            authenticationService.loginPerson(person)
            def preLoginURLKey = 'preLoginURL'
            def preLoginURL = session[preLoginURLKey]
            session[preLoginURLKey] = null
            preLoginURL = preLoginURL ? preLoginURL : '/'
            redirect(uri: preLoginURL)
        } else {
            redirect(action: login, params: [loginStatus: 'failed', enteredEmail: email])
        }
    }

    def login = {
        [loginStatus: params.loginStatus, enteredEmail: params.enteredEmail]
    }

    def logout = {
        authenticationService.logout()
        redirect(action: login, params: [loginStatus: 'loggedOut'])
    }
    
    def changePassword = {
        def personInstance = Person.get(params.id)
        if (personInstance) {
            return [personInstance: personInstance]
        }
        else {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
            redirect(action: LIST)    
        }
    }
    def updatePassword = {
        def personInstance = Person.get(params.id)
        if (personInstance) {
            if(params.old_password){
                authenticationService.changePassword(params.old_password, params.password, personInstance)
            }
            if (!personInstance.hasErrors() && personInstance.save(FLUSH)) {
                flash.message = makeMessage('default.updated.message', 'Password')
                redirect(action: EDIT, id: personInstance.id)
            }
            else {
                render(view: 'changePassword', model: [personInstance: personInstance])
            }
        }
        else {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
            redirect(action: LIST)
        }
    }
    private makeMessage(code, personId) {
        return "${message(code: code, args: [personLabel(), personId])}"
    }

    private personLabel() {
        message(code: PERSON_LABEL, default: 'Person')
    }
}
