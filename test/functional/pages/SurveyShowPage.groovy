package pages

import geb.Page

class SurveyShowPage extends Page {

    static url = "survey/show/1"

    static at = { title =~ /Show Survey/ }

    static content = {
        homeButton() { $("a", text: "Home") }
        surveyListButton() { $("a", text: "Survey List") }
        surveyEditButton(to: SurveyEditPage) { $('input', value:'Edit') }
        surveyAssignButton() { $('a', text:'Assign') }
    }
}
