import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*


class SurveyAssignmentPageSpec extends GebReportingSpec {
    
    private login(email, password) {
        to LoginPage
        loginEmailField.value(email)
        loginPasswordField.value(password)
        loginButton.click()
    }
    
    def "home button on survey assignment create should go to home page"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to SurveyAssignmentCreatePage
        homeButton.click()
        
        then:
        at HomePage
    }
    
    def "survey assignment list button on survey assignment create should go to survey assignment list"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to SurveyAssignmentCreatePage
        surveyAssignmentListButton.click()
        
        then:
        at SurveyAssignmentListPage
    }
    
    def "assign button on survey assignment create should go to survey show"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to SurveyAssignmentCreatePage
        surveyAssignButton.click()
       
        then:
        at SurveyShowPage
    }

}
