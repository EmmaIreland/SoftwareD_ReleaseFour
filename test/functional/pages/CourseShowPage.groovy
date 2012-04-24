package pages
import geb.Page
class CourseShowPage extends Page {
    static url = "course/show/1"
    static at = {
        title =~ /Show Course/
    }
    static content = {
        homeButton() { $("a", text: "Home") }
        courseListButton() { $("a", text: "Course List") }
        newCourseButton() { $("a", text: "New Course") }
        courseEditButton(to: CourseEditPage) { $('input', value:'Edit') }
        addOrRemoveAStudent() { $('a', text:'Add or remove a student') }
    }
}