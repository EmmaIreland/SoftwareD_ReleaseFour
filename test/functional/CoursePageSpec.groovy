import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*
import pages.CourseShowPage
import pages.CourseCreatePage
import pages.CourseEditPage
import pages.CourseListPage
import pages.ProjectCreatePage
import pages.EnrollmentCreatePage

class CoursePageSpec extends GebReportingSpec {

    def setup() {
        setup:
        to LoginPage
        loginEmailField.value('sid@anderson.net')
        loginPasswordField.value('shiboleet')
        loginButton.click()     
    }

    def "home button on course list page should go to home page"() {
        when:
        to CourseListPage
        homeButton.click()

        then:
        at HomePage
    }

    def "new course button on course list page should go to course create"() {
        when:
        to CourseListPage
        newCourseButton.click()

        then:
        at CourseCreatePage
    }

    def "course create should go to course show"() {
        when:
        to CourseCreatePage
        courseAbbreviationBox.value("SA")
        courseNameBox.value("Some Name")
        courseCreateButton.click()

        then:
        at CourseShowPage
    }

    def "new course button on course show page should go to course create"() {
        when:
        to CourseShowPage
        newCourseButton.click()

        then:
        at CourseCreatePage
    }

    def "course list button on course show page should go to course list"() {
        when:
        to CourseCreatePage
        courseAbbreviationBox.value("SA")
        courseNameBox.value("Some Name")
        courseCreateButton.click()
        courseListButton.click()

        then:
        at CourseListPage
    }
    
    def "Add or remove a student button on course show page should go to course create"() {
        when:
        to CourseShowPage
        addOrRemoveAStudent.click()

        then:
        at EnrollmentCreatePage
    }
}