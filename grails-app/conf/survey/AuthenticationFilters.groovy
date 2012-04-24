package survey

class AuthenticationFilters {
    def noPermissionURL = '/nopermission'
    
    def filters = {
        courseModification(controller:'course', action: '(create|edit|delete|update|save)') {
            before = {
                def user = Person.get(session['user'])
                if ( user != null && !user.isAdmin ) {
                    println '----------------------------------------'
                    println 'controllerName: ' + controllerName
                    println 'actioName: ' + actionName
                    println 'request: ' + request
                    println 'params: ' + params
                    println '----------------------------------------'
                    redirect(uri: noPermissionURL)
                    return false
                }
            }
        }
        
        coursePrivacy(controller:'course', action: 'show') {
            before = {
                def user = Person.get(session['user'])
                def course = Course.get(params.id)
                if ( user != null &&
                     !user.isAdmin &&
                     !personIsEnrolledInCourse(user, course) ) {
                    redirect(uri: noPermissionURL)
                    return false
                }
            }
        }
	
	personPrivacy(controller:'person', action: '(create|edit|delete|update|save|show|list)') {
	    before = {
		def user = Person.get(session['user'])
		if ( user != null &&
                     !user.isAdmin &&
                     user.id.toString() != params.id.toString() ) {
                    redirect(uri: noPermissionURL)
                    return false
                }
		    
	    }
	}
    }
    
    def personIsEnrolledInCourse(person, course) {
        def courses = person.enrollments*.course
        courses.contains(course)
    }
    
}
