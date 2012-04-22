package survey

class RedirectLoginTagLib {
    /**
     * @attr currentURL REQUIRED if at login, don't redirect
     */
    def redirectToLogin = { attrs, body ->
        if ( !(attrs['currentURL'] =~ '/*/login*') ) {
            def urlParamMap = request.parameterMap;
            session['preLoginURL'] = attrs['currentURL'] + urlParamsToString(urlParamMap)
            response.sendRedirect("${request.contextPath}/person/login")
        }
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
