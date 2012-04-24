package pages

import geb.Page

class SurveyAssignmentListPage extends Page{
    static url = "surveyAssignment/list"
    
    static at = {
        title =~ /SurveyAssignment List/
    }
    
    static content = {
        homeButton() { $("a", text: "Home") }
    }

}