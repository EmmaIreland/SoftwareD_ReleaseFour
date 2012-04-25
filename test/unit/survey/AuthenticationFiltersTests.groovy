package survey

import grails.test.*

class AuthenticationFiltersTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testUrlParamsToStringSingle() {
        Map paramMap = [factor:['1']]
        assertEquals '?factor=1', AuthenticationFilters.urlParamsToString(paramMap)
    }

    void testUrlParamsToStringMultiple() {
        Map paramMap = [derp:['mic'], herp:['z0']]
        assertEquals '?derp=mic&herp=z0', AuthenticationFilters.urlParamsToString(paramMap)
    }
}
