package survey

class RedirectLoginTagLib {
	/**
	 * @attr currentURL REQUIRED if at login, don't redirect
	 */
	def redirectToLogin = { attrs, body ->
		if ( !(attrs['currentURL'] =~ '/*/login') ) {
                        session['preLoginURL'] = attrs['currentURL']
			response.sendRedirect("${request.contextPath}/person/login")
		}
	}
}
