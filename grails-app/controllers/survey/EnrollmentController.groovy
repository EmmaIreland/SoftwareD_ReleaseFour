package survey

class EnrollmentController extends ControllerAssist {

    static allowedMethods = [save: POST, update: POST, delete: POST]

    def index = {
        redirect(action: LIST, params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [enrollmentInstanceList: Enrollment.list(params), enrollmentInstanceTotal: Enrollment.count()]
    }

    def create = {
        def enrollmentInstance = new Enrollment()
        def availableStudents = Person.list()
        
        def course = Course.get(params.course.id)
        
        if (course) {
            availableStudents = availableStudents - course.enrollments*.person - course.owner
        } else {
            redirect(uri: '/404')
            return []
        }

        enrollmentInstance.properties = params

        return [enrollmentInstance: enrollmentInstance, availableStudents: availableStudents.sort {it.name},
			hasAvailableStudents: (availableStudents.size() > 0)]
    }

    def save = {
        def personInstance
        if (params?.name != null) {
            personInstance = new Person(params)
            personInstance.save(FLUSH)
        }

        def enrollmentInstance = personInstance ? new Enrollment(person: personInstance,
			course: Course.get(params.course.id)) : new Enrollment(params)
        if (enrollmentInstance.save(FLUSH)) {
            redirect(controller: 'enrollment', action: CREATE,
				 params:['course.id': enrollmentInstance.course.id])
        }
        else {
            enrollmentInstance.errors.rejectValue('person', 'enrollment.person.unique')
            render(view: CREATE, model: [enrollmentInstance: enrollmentInstance])
        }
    }

    def show = {
        def enrollmentInstance = Enrollment.get(params.id)
        if (enrollmentInstance) {
			[enrollmentInstance: enrollmentInstance]
        }
        else {
			flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
        }
    }

    def edit = {
        def enrollmentInstance = Enrollment.get(params.id)
        if (enrollmentInstance) {
			return [enrollmentInstance: enrollmentInstance]
        }
        else {
			flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
        }
    }

    def update = {
        def enrollmentInstance = Enrollment.get(params.id)
        if (enrollmentInstance) {
            if (params.version) {
				versionCheck(enrollmentInstance, params.version, ENROLLMENT_LABEL, 'Enrollment')
            }
            enrollmentInstance.properties = params
            if (!enrollmentInstance.hasErrors() && enrollmentInstance.save(FLUSH)) {
                flash.message = makeMessage('default.updated.message', enrollmentInstance.id)
                redirect(action: SHOW, id: enrollmentInstance.id)
            }
            else {
                render(view: EDIT, model: [enrollmentInstance: enrollmentInstance])
            }
        }
        else {
            flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
            redirect(action: LIST)
        }
    }

    def delete = {
        def enrollmentInstance = Enrollment.get(params.id)
        enrollmentInstance.delete(FLUSH)
        render('Success')
    }
    
    def deleteByPerson = {
        def course = Course.get(params.courseId)
        def person = Person.get(params.personId)
        Enrollment.findByCourseAndPerson(course, person).delete(FLUSH)
    }

    private makeMessage(code, enrollmentId) {
        return "${message(code: code, args: [enrollmentLabel(), enrollmentId])}"
    }

    private enrollmentLabel() {
        message(code: ENROLLMENT_LABEL, default: 'Enrollment')
    }
}
