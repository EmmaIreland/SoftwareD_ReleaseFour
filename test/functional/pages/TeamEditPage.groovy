package pages

import geb.Page

class TeamEditPage extends Page {

    static url = "team/edit/1"

    static at = { title =~ /Edit Group/ }

    static content = {
        homeButton() { $("a", text: "Home") }
        teamNameBox() { $('input', name: 'name') }
        teamCommentBox() { $('textarea', name: 'comments') }
        teamUpdateButton(to: ProjectShowPage) { $('input', value:'Update') }
    }
}
