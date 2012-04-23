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
        courseAbbreviationBox.value('SN')
        courseNameBox.value('Some Name 101')
        courseTermBox.value('Spring')
        courseYearBox.value('2012')
        courseCreateButton.click()

        then:
        at CourseShowPage
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

    def "course edit should go to course show"() {
        when:
        to CourseEditPage
        courseAbbreviationBox.value("CSCI 1601")
        courseNameBox.value("Software R")
        courseTermBox.value('May')
        courseYearBox.value('2012')
        courseUpdateButton.click()

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

    def "course edit button on course show goes to course edit"() {
        when:
        to CourseShowPage
        courseEditButton.click()

        then:
        at CourseEditPage
    }

    def "Add or remove a student button on course show page should go to course create"() {
        when:
        to CourseShowPage
        addOrRemoveAStudent.click()

        then:
        at EnrollmentCreatePage
    }
    
    def "delete button should go to course list"() {
        when:
        to CourseShowPage
        withConfirm { courseDeleteButton.click() }
        
        then:
        at CourseListPage
    }
    
    def "course delete on edit goes to course list"() {
        when:
        to CourseCreatePage
        courseAbbreviationBox.value("Abbreviation")
        courseNameBox.value("Name")
        courseTermBox.value('Fall')
        courseYearBox.value('2013')
        courseCreateButton.click()
        
        courseEditButton.click()
        withConfirm { courseDeleteButton.click() }
        
        then:
        at CourseListPage
    }
}