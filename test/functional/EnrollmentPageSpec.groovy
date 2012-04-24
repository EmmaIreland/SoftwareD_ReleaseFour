import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*
import pages.EnrollmentCreatePage


class EnrollmentPageSpec extends GebReportingSpec {

    private login(email, password) {
        to LoginPage
        loginEmailField.value(email)
        loginPasswordField.value(password)
        loginButton.click()
    }

    def "home button on enrollment create page should go to home page"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to EnrollmentCreatePage
        homeButton.click()
        
        then:
        at HomePage
    }
}