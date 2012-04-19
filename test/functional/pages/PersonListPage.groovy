package pages

import geb.Page

class PersonListPage extends Page{
    static url = "person/list"
    
    static at = {
	title =~ /Person List/
    }
    
    static content = {
        homeButton() { $("a", text: "Home") }
        newPersonButton() { $("a", text: "New Person") }
    }

}
