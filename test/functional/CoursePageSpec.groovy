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
    
    private login(email, password) {
        to LoginPage
        loginEmailField.value(email)
        loginPasswordField.value(password)
        loginButton.click()
    }
    
    def "home button on course list page should go to home page"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to CourseListPage
        homeButton.click()
        
        then:
        at HomePage
    }
    
    def "new course button on course list page should go to course create"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to CourseListPage
        newCourseButton.click()
        
        then:
        at CourseCreatePage
    }
    
    def "course create should go to course show"() {
        when:
        login('sid@anderson.net', 'shiboleet')
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
        login('sid@anderson.net', 'shiboleet')
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
        login('sid@anderson.net', 'shiboleet')
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
        login('sid@anderson.net', 'shiboleet')
        to CourseShowPage
        newCourseButton.click()
        
        then:
        at CourseCreatePage
    }
    
    def "course edit button on course show goes to course edit"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to CourseShowPage
        courseEditButton.click()
        
        then:
        at CourseEditPage
    }
    
    def "Add or remove a student button on course show page should go to course create"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to CourseShowPage
        addOrRemoveAStudent.click()
        
        then:
        at EnrollmentCreatePage
    }
}