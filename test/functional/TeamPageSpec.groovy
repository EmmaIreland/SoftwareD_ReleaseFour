import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*

class TeamPageSpec extends GebReportingSpec {
    
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
    
    def "When update button is clicked it should go to the project show page"(){
        when:
        login('sid@anderson.net', 'shiboleet')
        to TeamEditPage
        teamNameBox.value("TheName!")
        teamCommentBox.value("TheComment!")
        teamUpdateButton.click()
        
        then:
        at ProjectShowPage
    }
    
    def "Manage students in group button goes to manage teams page"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to TeamEditPage
        manageStudentsInGroupButton.click()
        
        then:
        at TeamManageTeamsPage
    }
    
}
