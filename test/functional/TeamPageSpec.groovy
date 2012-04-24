import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*

class TeamPageSpec extends GebReportingSpec {
    
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
    
    def "When update button is clicked it should go to the project show page"(){
        when:
        to TeamEditPage
        teamNameBox.value("TheName!")
        teamCommentBox.value("TheComment!")
        teamUpdateButton.click()
        
        then:
        at ProjectShowPage
    }
    
    def "Manage students in group button goes to manage teams page"() {
        when:
        to TeamEditPage
        manageStudentsInGroupButton.click()
        
        then:
        at TeamManageTeamsPage
    }
    
}
