package pages

import geb.Page

class PersonShowPage extends Page {
    static url = "person/show/1"

    static at = { title =~ /Show Person/ }

    static content = {
        homeButton() { $("a", text: "Home") }
        personListButton() { $("a", text: "Person List") }
        newPersonButton() { $("a", text: "New Person") }
        personEditButton(to: PersonEditPage) { $('input', value:'Edit') }
    }
}
