package pages

import geb.Page

class ProjectEditPage extends Page {
    static url = "project/index"

    static at = { title =~ /Edit Project/ }

    static content = {
        homeButton() { $("a", text: "Home") }
        projectListButton() { $("a", text: "Project List") }
        newProjectButton() { $("a", text: "New Project") }
        projectNameBox() { $('input', name: 'name') }
        projectDescriptionBox() { $('input', name: 'description') }
        projectUpdateButton(to: ProjectShowPage) { $('input', value:'Update') }
    }
}
