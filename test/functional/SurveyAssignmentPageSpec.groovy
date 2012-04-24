import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*


class SurveyAssignmentPageSpec extends GebReportingSpec {
    
    def setup() {
        setup:
        to LoginPage
        loginEmailField.value('sid@anderson.net')
        loginPasswordField.value('shiboleet')
        loginButton.click()
    }
    
    def "home button on survey assignment create should go to home page"() {
        when:
        to SurveyAssignmentCreatePage
        homeButton.click()
        
        then:
        at HomePage
    }
    
    def "survey assignment list button on survey assignment create should go to survey assignment list"() {
        when:
        to SurveyAssignmentCreatePage
        surveyAssignmentListButton.click()
        
        then:
        at SurveyAssignmentListPage
    }

}
