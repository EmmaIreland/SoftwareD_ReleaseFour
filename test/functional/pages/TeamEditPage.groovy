package pages

import geb.Page

class TeamEditPage extends Page {

    static url = "team/edit/1"

    static at = { title =~ /Edit Group/ }

    static content = {
        homeButton() { $("a", text: "Home") }
        teamNameBox() { $('input', name: 'name') }
        teamCommentBox() { $('textarea', name: 'comments') }
        manageStudentsInGroupButton() { $("a", text: "Manage Students in Group") }
        teamUpdateButton(to: ProjectShowPage) { $('input', value:'Update') }
    }
}
