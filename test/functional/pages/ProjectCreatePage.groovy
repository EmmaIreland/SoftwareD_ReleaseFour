package pages

import geb.Page

class ProjectCreatePage extends Page {
    static url = "project/create?course.id=1"
    
    static at = {
	title =~ /Create Project/
    }
    
    static content = {
	
    }

}