import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*

class PersonPageSpec extends GebReportingSpec {
    
    def setup() {
        setup:
        to LoginPage
        loginEmailField.value('sid@anderson.net')
        loginPasswordField.value('shiboleet')
        loginButton.click()
    }

    def "home button on person list page should go to home page"() {
        when:
        to PersonListPage
        homeButton.click()

        then:
        at HomePage
    }

    def "new person button on person list page should go to person create"() {
        when:
        to PersonListPage
        newPersonButton.click()

        then:
        at PersonCreatePage
    }
}
