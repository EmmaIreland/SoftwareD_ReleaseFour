package pages

import geb.Page

class PersonChangePasswordPage extends Page {

    static url = "person/changePassword/1"
    
    static at = {
        title =~ /Edit Change Password/
    }
    
    static content = {
        homeButton() { $("a", text: "Home") }
        enterCurrentPasswordBox() { $('input', name: 'old_password') }
        enterNewPasswordBox() { $('input', name: 'password') }
        reEnterNewPasswordBox() { $('input', name: 'confirm_password') }
        updateButton(to: PersonEditPage) { $('input', value:'Update') }
    }
}
