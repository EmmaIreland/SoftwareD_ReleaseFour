package pages

import geb.Page

class ProjectCreatePage extends Page {
    static url = "project/create"
    
    static at = {
	title =~ /Create Project/
    }
    
    static content = {
	
    }

}