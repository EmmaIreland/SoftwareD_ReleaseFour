import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*


class QuestionPageSpec extends GebReportingSpec {
    
    private login(email, password) {
        to LoginPage
        loginEmailField.value(email)
        loginPasswordField.value(password)
        loginButton.click()
    }
    
    def "home button on question show should go to home page"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to QuestionShowPage
        homeButton.click()
        
        then:
        at HomePage
    }
    
    def "home button on question list should go to home page"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to QuestionListPage
        homeButton.click()
        
        then:
        at HomePage
    }
    
    def "question list button on question show should go to question list"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to QuestionShowPage
        questionListButton.click()
        
        then:
        at QuestionListPage
    }
}
