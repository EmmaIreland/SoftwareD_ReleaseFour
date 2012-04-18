package pages

import geb.Page

class SurveyCreatePage extends Page {
	static url = "survey/create?project.id=1"
	
	static at = {
	    title =~ /Create Survey/
	}
	
	static content = {
	    
	}
    
}
