package pages

import geb.Page

class PersonEditPage extends Page {
    static url = "person/edit/1"

    static at = { title =~ /Edit Person/ }

    static content = {
        homeButton() { $("a", text: "Home") }
        personNameBox() { $('input', name: 'name') }
        personEmailBox() { $('input', name: 'email') }
        personUpdateButton(to: PersonShowPage) { $('input', value:'Update') }
        changePasswordButton() { $("a", text: "Change Password") }
    }
}
