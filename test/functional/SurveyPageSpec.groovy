import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*


class SurveyPageSpec extends GebReportingSpec {
    
    private login(email, password) {
        to LoginPage
        loginEmailField.value(email)
        loginPasswordField.value(password)
        loginButton.click()
    }
    
    def "home button on survey list page should go to home page"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to SurveyListPage
        homeButton.click()
       
        then:
        at HomePage
    }
    
    def "survey list button on survey show should go to survey list"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to SurveyShowPage
        surveyListButton.click()
        
        then:
        at SurveyListPage
    }
   
    def "edit button on survey show should go to survey edit"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to SurveyShowPage
        surveyEditButton.click()
       
        then:
        at SurveyEditPage
    }
    
    def "assign button on survey show should go to survey assignment create"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to SurveyShowPage
        surveyAssignButton.click()
       
        then:
        at SurveyAssignmentCreatePage
    }
   
    def "update button on survey edit goes to survey show"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to SurveyEditPage
        surveyTitleBox.value('Updated Survey')
        surveyUpdateButton.click()
       
        then:
        at SurveyShowPage
    }
    
}
