import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*
import pages.ProjectListPage
import pages.ProjectShowPage
import pages.ProjectCreatePage
import pages.ProjectEditPage
import pages.SurveyCreatePage


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
    
    def "home button on project show page should go to home page"() {
        when:
        to ProjectShowPage
        homeButton.click()
        then:
        at HomePage
    }
    
    def "create button on project create should go to project show"() {
        when:
        to ProjectCreatePage
        projectNameBox.value('New Project')
        projectDescriptionBox.value('This is a new project for you')
        projectCreateButton.click()
       
        then:
        at ProjectShowPage
    }
   
    def "create survey button on project show page should go to survey create"() {
        when:
        to ProjectShowPage
        createNewSurveyButton.click()
        then:
        at SurveyCreatePage
    }
    
    def "edit button on project show goes to project edit"() {
        when:
        to ProjectShowPage
        projectEditButton.click()
        then:
        at ProjectEditPage
    }
    
    def "project edit should go to project show"() {
        when:
        to ProjectEditPage
        projectNameBox.value("Project 1")
        projectDescriptionBox.value("Software Ree")
        projectUpdateButton.click()
        then:
        at ProjectShowPage
    }
}