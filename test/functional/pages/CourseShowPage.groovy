package pages

import geb.Page

class CourseShowPage extends Page {
    static url = "course/show"

    static at = {
        title =~ /Show Course/
    }

    static content = {
        homeButton() { $("a", text: "Home") }
        courseListButton() { $("a", text: "Course List") }
        newCourseButton() { $("a", text: "New Course") }
        courseEditButton(to: CourseEditPage) { $('a', text:'Edit') }
        courseDeleteButton() { $('a', text:'Delete') }
        addProjectButton(to: ProjectCreatePage) { $("a", value: "Questions")}
    }
}
