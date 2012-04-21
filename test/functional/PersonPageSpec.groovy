import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*
import pages.PersonShowPage
import pages.PersonEditPage

class PersonPageSpec extends GebReportingSpec {
    
    def setup() {
        setup:
        to LoginPage
        loginEmailField.value('sid@anderson.net')
        loginPasswordField.value('shiboleet')
        loginButton.click()
    }

    def "home button on person list page should go to home page"() {
        when:
        to PersonListPage
        homeButton.click()

        then:
        at HomePage
    }

    def "new person button on person list page should go to person create"() {
        when:
        to PersonListPage
        newPersonButton.click()

        then:
        at PersonCreatePage
    }
    
    def "create button on person create should go to person show"() {
        when:
        to PersonCreatePage
        personNameBox.value('Bob')
        personEmailBox.value('bob@bob.com')
        personPasswordBox.value('bbbbb')
        personReEnterPasswordBox.value('bbbbb')
        personCreateButton.click()
        
        then:
        at PersonShowPage
    }
    
    def "edit button on person show page should go to person edit"() {
        when:
        to PersonShowPage
        personEditButton.click()
        
        then:
        at PersonEditPage
    }
    
    def "update on edit goes to person show"() {
        when:
        to PersonEditPage
        personNameBox.value('Oprah')
        personUpdateButton.click()
        
        then:
        at PersonShowPage
    }
    
    def "change password on person edit goes to person change password page"() {
        when:
        to PersonEditPage
        changePasswordButton.click()
        
        then:
        at PersonChangePasswordPage
    }

}
