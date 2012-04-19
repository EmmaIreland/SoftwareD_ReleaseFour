package pages

import geb.Page

class EnrollmentCreatePage extends Page {
    static url = "enrollment/create?course.id=1"

    static at = { title =~ /Create Enrollment/ }

    static content = {
        homeButton() { $('a', text: 'Home') }
    }
}
