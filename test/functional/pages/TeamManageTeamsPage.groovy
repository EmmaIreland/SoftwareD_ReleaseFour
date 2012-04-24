package pages

import geb.Page

class TeamManageTeamsPage extends Page {

    static url = "team/list?project=1"

    static at = { title =~ /Manage Groups/ }

    static content = {
        homeButton() { $("a", text: "Home") }
    }
}
