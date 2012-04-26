package survey

import org.apache.catalina.Session;

class AuthenticationFilters extends ControllerAssist {
	
    // protectedActions are all actions except login, logout, sendLogin
    def protectedActions = '(create|list|show|edit|update|save|delete|addmany|changeMember|deleteByPerson|changePassword|updatePassword|assign|addQuestion|preview|submit|take)'
    
    def filters = {
        loggedInHome(uri: '/') {
            before = {
                if ( session[USER_STRING] && !Person.get(session[USER_STRING]) ) {
                    session[USER_STRING] = null
                    redirect(PERSON_LOGIN_DELETED_MAP)
                    return false
                }
                if ( !session[USER_STRING] ) {
                    redirect(PERSON_LOGIN_MAP)
                    return false
                }
            }
        }
        
        loggedIn(controller:'*', action: protectedActions ) {
            before = {
                if ( session[USER_STRING] && !Person.get(session[USER_STRING]) ) {
                    session[USER_STRING] = null
                    redirect(PERSON_LOGIN_DELETED_MAP)
                    return false
                }
                if ( !session[USER_STRING] && !(request.forwardURI =~ '/*/login*') ) {
                    
                    def urlEnd = request.forwardURI.split('/')
                    urlEnd = urlEnd.size() == 2 ? [] : urlEnd[2..-1]
                    urlEnd = '/' + urlEnd.join('/')
                    
                    def urlParamMap = request.parameterMap;
                    session['preLoginURL'] = urlEnd + urlParamsToString(urlParamMap)
                    redirect(PERSON_LOGIN_MAP)
                    
                    return false
                }
            }
        }
        
        // prevents exception where user enters sendLogin url manually
        protectSendLogin(controller:PERSON_STRING, action:'sendLogin') {
            before = {
                if ( !(params[EMAIL_STRING] && params[PASSWORD_STRING]) ) {
                    redirect(PERSON_LOGIN_MAP)
                    return false
                }
            }
        }
        
        courseModification(controller:COURSE_STRING, action: '(create|edit|delete|update|save)') {
            before = {
                def user = Person.get(session[USER_STRING])
                if ( notNullNotAdmin(user) ) {
                    redirect(uri: NO_PERMISSION_URL)
                    return false
                }
            }
        }
		
		personModification(controller:PERSON_STRING, action: '(create|delete|save)') {
			before = {
				def user = Person.get(session[USER_STRING])
				if ( notNullNotAdmin(user) ) {
					redirect(uri: NO_PERMISSION_URL)
					return false
				}
			}
		}
        
		changePassword(controller:PERSON_STRING, action:'(changePassword|updatePassword)'){
			before = {
				def user = Person.get(session[USER_STRING])
				if(user && (user.id.toString() != params.id.toString())) {
					redirect(uri: NO_PERMISSION_URL)
					return false
				}
			}
		}
		
        coursePrivacy(controller:COURSE_STRING, action: SHOW) {
            before = {
                def user = Person.get(session[USER_STRING])
                def course = Course.get(params.id)
                if ( notNullNotAdmin(user) &&
                     !personIsEnrolledInCourse(user, course) ) {
                    redirect(uri: NO_PERMISSION_URL)
                    return false
                }
            }
        }
	
        // prevents non-admins from viewing other profiles except those belonging to themselves and their instructors
	personPrivacy(controller:PERSON_STRING, action: '(create|edit|delete|update|save|show|list)') {
	    before = {
		def user = Person.get(session[USER_STRING])
		if ( notNullNotAdmin(user) &&
                     user.id.toString() != params.id.toString() &&
                     !( (actionName == SHOW) &&
                        personHasAsInstructor(user, Person.get(params.id)) )) {
                    redirect(uri: NO_PERMISSION_URL)
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
