package survey

class ControllerAssist {

	//maps
	static final flush = [flush: true]
	static final failOnError = [failOnError: true]
	static final listMap = [action: listString]


	//action strings
	static final createString = 'create'
	static final editString = 'edit'
	static final listString = 'list'
	static final showString = 'show'

	//label strings
	static final personLabelString = 'person.label'
	static final projectLabelString = 'project.label'
	static final courseLabelString = 'course.label'
	static final surveyLabelString = 'survey.label'


	//other strings
	static final post = 'POST'
	static final defaultNotFoundMessage = 'default.not.found.message'



	// method for the version checking in update action
	public versionCheck(instance, paramsVersion, label, controllerString) {
		def version = paramsVersion.toLong()
		if (instance.version > version) {
			instance.errors.rejectValue('version', 'default.optimistic.locking.failure',
					[message(code: label, default: controllerString)] as Object[],
					'Another user has updated this while you were editing')
			render(view: 'edit', model: [instance: instance])
			return
		}
	}

}
