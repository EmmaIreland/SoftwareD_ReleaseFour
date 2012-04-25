import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*
import pages.HomePage
import pages.CourseCreatePage
import pages.CourseListPage
import pages.PersonListPage
import pages.LoginPage

class HomePageSpec extends GebReportingSpec {

    private login(email, password) {
        to LoginPage
        loginEmailField.value(email)
        loginPasswordField.value(password)
        loginButton.click()
    }

    def "can reach the home page"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to HomePage

        then:
        at HomePage
    }

    def "can see Course List"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to HomePage
        coursesButton.click()

        then:
        at CourseListPage
    }

    def "can reach the person list page"(){
        when:
        login('sid@anderson.net', 'shiboleet')
        to HomePage
        peopleButton.click()

        then:
        at PersonListPage
    }

    def "can reach the survey list page"(){
        when:
        login('sid@anderson.net', 'shiboleet')
        to HomePage
        surveysButton.click()

        then:
        at SurveyListPage
    }


    def "can reach the project list page"(){
        when:
        login('sid@anderson.net', 'shiboleet')
        to HomePage
        projectsButton.click()

        then:
        at ProjectListPage
    }

    def "redirect to Login page"(){
        when:
        login('sid@anderson.net', 'shiboleet')
        to HomePage
        logoutButton.click()
        to HomePage

        then:
        at LoginPage
    }
    
    def "A non admin can log out"() {
        when:
        login('oprah@oprah.com', 'password')
        to HomePage
        logoutButton.click()
        
        then:
        at LoginPage
    }
}