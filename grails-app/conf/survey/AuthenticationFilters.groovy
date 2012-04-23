package survey

import grails.util.GrailsWebUtil

class AuthenticationFilters {
    
    def filters = {
        courseModification(controller:'course', action: '(create|edit|delete|update|save)') {
            before = {
                def user = Person.get(session['user'])
                if ( user != null && !user.isAdmin ) {
                    //flash.message = "You do not have the permissions to access that page."
                    println 'url: ' + request.getRequestURI()
                    redirect(uri: '/nopermission', params: ['triedURL': request.getRequestURI()])
                    return false
                }
            }
        }
    }
    
}
