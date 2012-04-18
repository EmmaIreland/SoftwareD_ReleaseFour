package pages

import geb.Page

class ProjectShowPage extends Page {
    static url = "project/show/1"

    static at = {
        title =~ /Show Project/
    }

    static content = {
        homeButton() { $("a", text: "Home") }
        projectListButton() { $("a", text: "Project List") }
        newProjectButton() { $("a", text: "New Project") }
        createNewSurveyButton() { $("a", text: "Create new Survey") }
        projectEditButton(to: ProjectEditPage) { $('a', text:'Edit') }
        manageGroupsButton() { $('a', text:'Manage Groups') }
    }
}
