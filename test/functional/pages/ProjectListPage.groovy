package pages

import geb.Page

class ProjectListPage extends Page{
    static url = "project/list"
    
    static at = {
	title =~ /Project List/
    }
    
    static content = {
        homeButton() { $("a", text: "Home") }
        newProjectButton() { $("a", text: "New Project") }
    }

}