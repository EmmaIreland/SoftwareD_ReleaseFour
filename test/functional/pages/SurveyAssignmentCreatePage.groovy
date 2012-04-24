package pages

import geb.Page

class SurveyAssignmentCreatePage extends Page {
    
    static url = "surveyAssignment/create?surveyid=1"

    static at = { title =~ /Assign Survey/ }

    static content = {
        homeButton() { $("a", text: "Home") }
        surveyAssignmentListButton() { $("a", text: "SurveyAssignment List") }
        surveyAssignButton() { $('input', value:'Assign') }
    }
}
