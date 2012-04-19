package pages

import geb.Page

class CourseEditPage extends Page {
    static url = "course/edit/1"

    static at = { title =~ /Edit Course/ }

    static content = {
        homeButton() { $("a", text: "Home") }
        courseListButton() { $("a", text: "Course List") }
        newCourseButton() { $("a", text: "New Course") }
        courseAbbreviationBox() { $('input', name: 'abbreviation') }
        courseNameBox() { $('input', name: 'name') }
	courseTermBox() { $('select', name: 'term') }
        courseUpdateButton(to: CourseShowPage) { $('input', value:'Update') }
        courseDeleteButton(to: CourseListPage) { $('input', value:'Delete') }
    }
}
