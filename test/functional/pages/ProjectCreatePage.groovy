package pages

import geb.Page

class ProjectCreatePage extends Page {
    static url = "project/create?course.id=1"
    
    static at = {
	title =~ /Create Project/
    }
    
    static content = {
        homeButton() { $("a", text: "Home") }
        projectListButton() { $("a", text: "Project List") }
        projectNameBox() { $('input', name: 'name') }
        projectDescriptionBox() { $('textarea', name: 'description') }
        projectCreateButton() { $('input', value:'Create') }
    }

}