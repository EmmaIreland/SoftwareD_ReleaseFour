package survey

class EnrollmentController extends ControllerAssist {

    static allowedMethods = [save: post, update: post, delete: post]

    def index = {
        redirect(action: listString, params: params)
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
            personInstance.save(flush)
        }

        def enrollmentInstance = personInstance ? new Enrollment(person: personInstance,
			course: Course.get(params.course.id)) : new Enrollment(params)
        if (enrollmentInstance.save(flush)) {
            redirect(controller: 'enrollment', action: createString,
				 params:['course.id': enrollmentInstance.course.id])
        }
        else {
            enrollmentInstance.errors.rejectValue('person', 'enrollment.person.unique')
            render(view: createString, model: [enrollmentInstance: enrollmentInstance])
        }
    }

    def show = {
        def enrollmentInstance = Enrollment.get(params.id)
        if (enrollmentInstance) {
			[enrollmentInstance: enrollmentInstance]
        }
        else {
			flash.message = makeMessage(defaultNotFoundMessage, params.id)
			redirect(action: listString)
        }
    }

    def edit = {
        def enrollmentInstance = Enrollment.get(params.id)
        if (enrollmentInstance) {
			return [enrollmentInstance: enrollmentInstance]
        }
        else {
			flash.message = makeMessage(defaultNotFoundMessage, params.id)
			redirect(action: listString)
        }
    }

    def update = {
        def enrollmentInstance = Enrollment.get(params.id)
        if (enrollmentInstance) {
            if (params.version) {
				versionCheck(enrollmentInstance, params.version, enrollmentLabelString, 'Enrollment')
            }
            enrollmentInstance.properties = params
            if (!enrollmentInstance.hasErrors() && enrollmentInstance.save(flush)) {
                flash.message = makeMessage('default.updated.message', enrollmentInstance.id)
                redirect(action: showString, id: enrollmentInstance.id)
            }
            else {
                render(view: editString, model: [enrollmentInstance: enrollmentInstance])
            }
        }
        else {
            flash.message = makeMessage(defaultNotFoundMessage, params.id)
            redirect(action: listString)
        }
    }

    def delete = {
        def enrollmentInstance = Enrollment.get(params.id)
        enrollmentInstance.delete(flush)
        render('Success')
    }
    
    def deleteByPerson = {
        def course = Course.get(params.courseId)
        def person = Person.get(params.personId)
        Enrollment.findByCourseAndPerson(course, person).delete(flush)
    }

    private makeMessage(code, enrollmentId) {
        return "${message(code: code, args: [enrollmentLabel(), enrollmentId])}"
    }

    private enrollmentLabel() {
        message(code: enrollmentLabelString, default: 'Enrollment')
    }
}
