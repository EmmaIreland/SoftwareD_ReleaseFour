package pages

import geb.Page

class ProjectEditPage extends Page {
    static url = "project/edit/1"

    static at = { title =~ /Edit Project/ }

    static content = {
        homeButton() { $("a", text: "Home") }
        projectListButton() { $("a", text: "Project List") }
        newProjectButton() { $("a", text: "New Project") }
        projectNameBox() { $('input', name: 'name') }		
        projectDescriptionBox() { $('textarea', name: 'description') }
        projectUpdateButton(to: ProjectShowPage) { $('input', value:'Update') }
    }
}
