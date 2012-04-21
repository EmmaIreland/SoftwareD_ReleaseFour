package pages

import geb.Page

class SurveyCreatePage extends Page {
    static url = "survey/create?project.id=1"

    static at = { title =~ /Create Survey/ }

    static content = {
        homeButton() { $("a", text: "Home") }
        surveyListButton() { $("a", text: "Survey List") }
        templateBox() { $('select', name: 'existingSurvey') }
        surveyTitleBox() { $('input', name: 'title') }
        projectBox() { $('select', name: 'project.id') }
        surveyCreateButton(to: SurveyShowPage) { $('input', value:'Create') }
    }
}
