package survey

class ProjectController extends ControllerAssist {

	static allowedMethods = [save: POST, update: POST, delete: POST]

	def index = {
		redirect(action: LIST, params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[projectInstanceList: Project.list(params), projectInstanceTotal: Project.count()]
	}

	def create = {
		def projectInstance = new Project()
		projectInstance.properties = params
		return [projectInstance: projectInstance]
	}

	def save = {
		def projectInstance = new Project(params)
		if (projectInstance.save(FLUSH)) {
			flash.message = makeMessage('default.created.message', params.name)
			redirect(action: SHOW, id: projectInstance.id)
		}
		else {
			render(view: CREATE, model: [projectInstance: projectInstance])
		}
	}

	def show = {
		def projectInstance = Project.get(params.id)
		def numUnassignedStudents = getUnassignedStudents(projectInstance.course, projectInstance).size()
		if (projectInstance) {
			[projectInstance: projectInstance, numUnassignedStudents: numUnassignedStudents]
		}
		else {
			flash.message =  makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
		}
	}

	def edit = {
		def projectInstance = Project.get(params.id)
		if (projectInstance) {
			return [projectInstance: projectInstance]
		}
		else {
			flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
		}
	}

	def update = {
		def projectInstance = Project.get(params.id)
		if (projectInstance) {
			if (params.version) {
				versionCheck(projectInstance, params.version, PROJECT_LABEL, 'Project')
			}
			projectInstance.properties = params
			if (!projectInstance.hasErrors() && projectInstance.save(FLUSH)) {
				flash.message = makeMessage('default.updated.message', projectInstance.name)
				redirect(action: SHOW, id: projectInstance.id)
			}
			else {
				render(view: EDIT, model: [projectInstance: projectInstance])
			}
		}
		else {
			flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
		}
	}

	def delete = {
		def projectInstance = Project.get(params.id)
		def projectCourse = projectInstance.course
		if (projectInstance) {
			try {
				projectInstance.delete(FLUSH)
				flash.message = makeMessage('default.deleted.message', projectInstance.name)
				redirect(controller:'course', action: SHOW, id:projectCourse.id)
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = makeMessage('default.not.deleted.message', projectInstance.name)
				redirect(action: SHOW, id: params.id)
			}
		}
		else {
			flash.message = makeMessage(DEFAULT_NOTFOUND_MESSAGE, params.id)
			redirect(action: LIST)
		}
	}

	private makeMessage(code, projectId) {
		return "${message(code: code, args: [projectLabel(), projectId])}"
	}

	private projectLabel() {
		message(code: PROJECT_LABEL, default: 'Project')
	}

	private getUnassignedStudents(course, project) {
		def allStudents = course.enrollments*.person
		def assignedStudents = []
		project.teams.each() { team -> assignedStudents += team.memberships*.member}
		allStudents - assignedStudents
	}
}
