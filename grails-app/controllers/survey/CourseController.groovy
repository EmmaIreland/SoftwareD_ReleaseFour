package survey

class CourseController extends ControllerAssist {

    static allowedMethods = [save: POST, update: POST, delete: POST]

    def index = {
        redirect(action: LIST, params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [courseInstanceList: Course.list(params), courseInstanceTotal: Course.count()]
    }

    def create = {
        def courseInstance = new Course()
        courseInstance.properties = params
        return courseMap(courseInstance)
    }

    def save = {
        def courseInstance = new Course(params)
        if (courseInstance.save(FLUSH)) {
            flash.message = makeMessage('default.created.message', params.name)
            redirect(action: SHOW, id: courseInstance.id)
        }
        else {
            render(view: CREATE, model: courseMap(courseInstance))
        }
    }

    def show = {
        def courseInstance = Course.get(params.id)
        if (courseInstance) {
            if (params.addstudent) {
                [courseInstance: courseInstance, addStudent: true]
            }
            else {
                courseMap(courseInstance)
            }
        } else {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.name)
            redirect(LIST_MAP)
        }
        
    }

    def edit = {
        def courseInstance = Course.get(params.id)
        if (courseInstance) {
			return courseMap(courseInstance)
        }
        else {
			flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.name)
			redirect(LIST_MAP)
        }
    }

    def update = {
        def courseInstance = Course.get(params.id)
        if (courseInstance) {
            if (params.version) {
                versionCheck(courseInstance, params.version, COURSE_LABEL, 'Course')
            }
            courseInstance.properties = params
            if (!courseInstance.hasErrors() && courseInstance.save(FLUSH)) {
                flash.message = makeMessage('default.updated.message', params.name)
                redirect(action: SHOW, id: courseInstance.id)
            }
            else {
                render(view: EDIT, model: courseMap(courseInstance))
            }
        }
        else {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.name)
            redirect(LIST_MAP)
        }
    }

    def delete = {
        def courseInstance = Course.get(params.id)
        if (courseInstance) {
            try {
                courseInstance.delete(FLUSH)
                flash.message = 'Course Deleted'
                redirect(LIST_MAP)
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.name)
                redirect(action: SHOW, id: params.id)
            }
        }
        else {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.name)
            redirect(LIST_MAP)
        }
    }

    private makeMessage(code, instanceId) {
        "${message(code: code, args: [label, instanceId])}"
    }

    private getLabel() {
        message(code: COURSE_LABEL, default: '')
    }

    private courseMap(courseInstance) {
        [courseInstance: courseInstance]
    }
}
