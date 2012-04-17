package pages

import geb.Page

class EnrollmentCreatePage extends Page {
    static url = "enrollment/create"

    static at = { title =~ /Create Enrollment/ }

    static content = {
        homeButton() { $("a", text: "Home") }
    }
}
