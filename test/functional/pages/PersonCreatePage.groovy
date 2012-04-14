package pages

import geb.Page

class PersonCreatePage extends Page{
    static url = "person/create"
    
    static at = {
	title =~ /Person Create/
    }
    
    static content = {
	create
    }

}