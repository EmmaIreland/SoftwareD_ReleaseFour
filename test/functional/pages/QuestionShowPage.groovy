package pages

import geb.Page

class QuestionShowPage extends Page {
    static url = "question/show/1"

    static at = { title =~ /Show Question/ }

    static content = {
        homeButton() { $("a", text: "Home") }
        questionListButton() { $("a", text: "Question List") }
    }
}
