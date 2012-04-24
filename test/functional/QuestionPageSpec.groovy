import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*


class QuestionPageSpec extends GebReportingSpec {
    
    def setup() {
        setup:
        to LoginPage
        loginEmailField.value('sid@anderson.net')
        loginPasswordField.value('shiboleet')
        loginButton.click()
    }
    
    def "home button on question show should go to home page"() {
        when:
        to QuestionShowPage
        homeButton.click()
        
        then:
        at HomePage
    }
    
    def "home button on question list should go to home page"() {
        when:
        to QuestionListPage
        homeButton.click()
        
        then:
        at HomePage
    }
    
    def "question list button on question show should go to question list"() {
        when:
        to QuestionShowPage
        questionListButton.click()
        
        then:
        at QuestionListPage
    }
}
