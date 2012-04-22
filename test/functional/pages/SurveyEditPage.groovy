package pages

import geb.Page

class SurveyEditPage extends Page {
    static url = "survey/edit/1"

    static at = { title =~ /Edit Survey/ }

    static content = {
        homeButton() { $("a", text: "Home") }
        surveyTitleBox() { $('input', name: 'title') }
        surveyUpdateButton(to: SurveyShowPage) { $('input', value:'Update') }
    }
}
