import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*
import pages.ProjectListPage
import pages.ProjectCreatePage

class ProjectPageSpec extends GebReportingSpec {

    def setup() {
        setup:
        to LoginPage
        loginEmailField.value('sid@anderson.net')
        loginPasswordField.value('shiboleet')
        loginButton.click()
    }
    
    def "home button on project list page should go to home page"() {
        when:
        to ProjectListPage
        homeButton.click()

        then:
        at HomePage
    }

    def "new project button on project list page should go to project create"() {
        when:
        to ProjectListPage
        newProjectButton.click()

        then:
        at ProjectCreatePage
    }
    
    def "home button on project show page should go to home page"() {
        when:
        to ProjectShowPage
        homeButton.click()

        then:
        at HomePage
    }
    
    def "new project button on project show page should go to project create"() {
        when:
        to ProjectShowPage
        newProjectButton.click()

        then:
        at ProjectCreatePage
    }
    

}
