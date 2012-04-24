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
   
    def "edit button on survey show should go to survey edit"() {
        when:
        to SurveyShowPage
        surveyEditButton.click()
       
        then:
        at SurveyEditPage
    }
   
    def "update button on survey edit goes to survey show"() {
        when:
        to SurveyEditPage
        surveyTitleBox.value('Updated Survey')
        surveyUpdateButton.click()
       
        then:
        at SurveyShowPage
    }
}
