import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*


class SurveyPageSpec extends GebReportingSpec {

    def setup() {
        setup:
        to LoginPage
        loginEmailField.value('sid@anderson.net')
        loginPasswordField.value('shiboleet')
        loginButton.click()
    }

    def "home button on survey list page should go to home page"() {
        when:
        to SurveyListPage
        homeButton.click()

        then:
        at HomePage
    }

    def "new survey button on survey list page should go to survey create"() {
        when:
        to SurveyListPage
        newSurveyButton.click()

        then:
        at SurveyCreatePage
    }
}
