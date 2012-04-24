import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*
import pages.EnrollmentCreatePage


class EnrollmentPageSpec extends GebReportingSpec {
    
    def setup() {
        setup:
        to LoginPage
        loginEmailField.value('sid@anderson.net')
        loginPasswordField.value('shiboleet')
        loginButton.click()
    }
   
    def "home button on enrollment create page should go to home page"() {
        when:
        to EnrollmentCreatePage
        homeButton.click()
        then:
        at HomePage
    }
   
}