package survey

class AuthenticationFilters {
    def noPermissionURL = '/nopermission'
	def courseString = 'course'
	def userString = 'user'
    
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
                return true
            }
        }
        
        loggedIn(controller:'*', action: '(create|list|show|edit|update|save|addmany|changeMember|deleteByPerson|changePassword|updatePassword|assign|addQuestion|preview|submit|take)') {
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
                } else {
                    return true
                }
            }
        }
        
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
    
    def urlParamsToString (Map urlParamMap) {
        def urlParamString = ''
        if ( urlParamMap.size() > 0 ) {
            urlParamString += '?'
            def index = 0
            urlParamMap.each { key, value ->
                urlParamString += key + '=' + value[0]
                if ( index != urlParamMap.size() - 1 ) {
                    urlParamString += '&'
                }
                index++
            }
        }
        urlParamString
    }
    
}
