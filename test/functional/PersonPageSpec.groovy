import geb.spock.GebReportingSpec
import spock.lang.*
import pages.*
import pages.PersonShowPage
import pages.PersonEditPage

class PersonPageSpec extends GebReportingSpec {
    
    private login(email, password) {
        to LoginPage
        loginEmailField.value(email)
        loginPasswordField.value(password)
        loginButton.click()
    }

    def "home button on person list page should go to home page"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to PersonListPage
        homeButton.click()

        then:
        at HomePage
    }

    def "new person button on person list page should go to person create"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to PersonListPage
        newPersonButton.click()

        then:
        at PersonCreatePage
    }
    
    def "create button on person create should go to person show"() {
        when:
        login('sid@anderson.net', 'shiboleet')
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
        login('sid@anderson.net', 'shiboleet')
        to PersonShowPage
        personEditButton.click()
        
        then:
        at PersonEditPage
    }
    
    def "update on edit goes to person show"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to PersonEditPage
        personNameBox.value('Oprah')
        personUpdateButton.click()
        
        then:
        at PersonShowPage
    }
    
    def "change password on person edit goes to person change password page"() {
        when:
        login('sid@anderson.net', 'shiboleet')
        to PersonEditPage
        changePasswordButton.click()
        
        then:
        at PersonChangePasswordPage
    }

}
