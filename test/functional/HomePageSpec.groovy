import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*
import pages.HomePage
import pages.CourseCreatePage
import pages.CourseListPage
import pages.PersonListPage
import pages.LoginPage

class HomePageSpec extends GebReportingSpec {

	def setup() {
		setup:
        to LoginPage
        loginEmailField.value('sid@anderson.net')
        loginPasswordField.value('shiboleet')
        loginButton.click()
	}
	
    def "can reach the home page"() {
	when:
	to HomePage

	then:
	at HomePage
    }

    def "can see Course List"() {
	when:
	to HomePage
	coursesButton.click()

	then:
	at CourseListPage
    }
    
    def "can reach the person list page"(){
	when:
	to HomePage
	peopleButton().click()
	
	then:
	at PersonListPage
	
    }
    
    def "can reach the survey list page"(){
	when:
	to HomePage
	surveysButton().click()
	
	then:
	at SurveyListPage
    }
    
    
    def "can reach the project list page"(){
	when:
	to HomePage
	projectsButton().click()
	
	then:
	at ProjectListPage
    }
    
    def "can reach the login page"(){
        when:
        to HomePage
		logoutButton().click()
		to HomePage
        loginButton().click()
        
        then:
        at LoginPage
    }
}