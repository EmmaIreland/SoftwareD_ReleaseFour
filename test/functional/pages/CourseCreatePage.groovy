package pages

import geb.Page

class CourseCreatePage extends Page {
    static url = "course/create"

    static at = { title =~ /Create Course/ }

    static content = {
        homeButton() { $("a", text: "Home") }
        courseListButton() { $("a", text: "Course List") }
        courseAbbreviationBox() { $('input', name: 'abbreviation') }
        courseNameBox() { $('input', name: 'name') }
        courseTermBox() { $('select', name: 'term') }
        courseYearBox() { $('select', name: 'year') }
        courseOwnerBox() { $('select', name: 'owner') }
        courseCreateButton(to: CourseShowPage) { $('input', value:'Create') }
    }
}
