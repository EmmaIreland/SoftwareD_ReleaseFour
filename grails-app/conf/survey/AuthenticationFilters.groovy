package survey

class AuthenticationFilters {
    def noPermissionURL = '/nopermission'
	def courseString = 'course'
	def userString = 'user'
    
    def filters = {
        courseModification(controller:courseString, action: '(create|edit|delete|update|save)') {
            before = {
                def user = Person.get(session[userString])
                if ( user != null && !user.isAdmin ) {
                    redirect(uri: noPermissionURL)
                    return false
                }
            }
        }
        
        coursePrivacy(controller:courseString, action: 'show') {
            before = {
                def user = Person.get(session[userString])
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
		def user = Person.get(session[userString])
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
