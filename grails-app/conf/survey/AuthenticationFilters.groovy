package survey

class AuthenticationFilters {
    def noPermissionURL = '/nopermission'
    def courseString = 'course'
    def userString = 'user'
    // protectedActions are all actions except login, logout, sendLogin
    def protectedActions = '(create|list|show|edit|update|save|delete|addmany|changeMember|deleteByPerson|changePassword|updatePassword|assign|addQuestion|preview|submit|take)'
    
    def filters = {
        loggedInHome(uri: '/') {
            before = {
                if ( session['user'] && !Person.get(session['user']) ) {
                    session['user'] = null
                    redirect(controller: 'person', action: 'login', params: ['loginStatus': 'deleted'])
                    return false
                }
                if ( !session['user'] ) {
                    redirect(controller: 'person', action: 'login')
                    return false
                }
            }
        }
        
        loggedIn(controller:'*', action: protectedActions ) {
            before = {
                if ( session['user'] && !Person.get(session['user']) ) {
                    session['user'] = null
                    redirect(controller: 'person', action: 'login', params: ['loginStatus': 'deleted'])
                    return false
                }
                if ( !session['user'] && !(request.forwardURI =~ '/*/login*') ) {
                    
                    def urlEnd = request.forwardURI.split('/')
                    urlEnd = urlEnd.size() == 2 ? [] : urlEnd[2..-1]
                    urlEnd = '/' + urlEnd.join('/')
                    
                    def urlParamMap = request.parameterMap;
                    session['preLoginURL'] = urlEnd + urlParamsToString(urlParamMap)
                    redirect(controller: 'person', action: 'login')
                    
                    return false
                }
            }
        }
        
        // prevents exception where user enters sendLogin url manually
        protectSendLogin(controller:'person', action:'sendLogin') {
            before = {
                if ( !(params['email'] && params['password']) ) {
                    redirect(controller: 'person', action: 'login')
                    return false
                }
            }
        }
        
        courseModification(controller:courseString, action: '(create|edit|delete|update|save)') {
            before = {
                def user = Person.get(session[userString])
                if ( notNullNotAdmin(user) ) {
                    redirect(uri: noPermissionURL)
                    return false
                }
            }
        }
        
        coursePrivacy(controller:courseString, action: 'show') {
            before = {
                def user = Person.get(session[userString])
                def course = Course.get(params.id)
                if ( notNullNotAdmin(user) &&
                     !personIsEnrolledInCourse(user, course) ) {
                    redirect(uri: noPermissionURL)
                    return false
                }
            }
        }
	
        // prevents non-admins from viewing other profiles except those belonging to themselves and their instructors
	personPrivacy(controller:'person', action: '(create|edit|delete|update|save|show|list)') {
	    before = {
		def user = Person.get(session[userString])
		if ( notNullNotAdmin(user) &&
                     user.id.toString() != params.id.toString() &&
                     !( (actionName == 'show') &&
                        personHasAsInstructor(user, Person.get(params.id)) )) {
                    println 'Person is trying to see instructor: ' + ((actionName == 'show') && personHasAsInstructor(user, Person.get(params.id)))
                    redirect(uri: noPermissionURL)
                    return false
                }
	    }
	}
    }
    
    def notNullNotAdmin(user) {
        user && !user.isAdmin
    }
    
    def personIsEnrolledInCourse(user, course) {
        def courses = user.enrollments*.course
        courses.contains(course)
    }
    
    def personHasAsInstructor(user, instructor) {
        user.enrollments*.course.owner.contains(instructor)
    }
    
    static urlParamsToString (Map urlParamMap) {
        def urlParamString = ''
        if ( urlParamMap.size() > 0 ) {
            urlParamString += '?'
            def index = 0
            urlParamMap.each { key, value ->
                urlParamString += key + '=' + value[0] // values are size-1 lists
                if ( index != urlParamMap.size() - 1 ) {
                    urlParamString += '&'
                }
                index++
            }
        }
        urlParamString
    }
    
}
