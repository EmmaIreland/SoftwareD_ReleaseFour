package pages

import geb.Page

class PersonCreatePage extends Page{
    static url = "person/create"
    
    static at = {
	title =~ /Create Person/
    }
    
    static content = {
        personNameBox() { $('input', name: 'name') }
        personEmailBox() { $('input', name: 'email') }
        personPasswordBox() { $('input', name: 'password') }
        personReEnterPasswordBox() { $('input', name: 'confirm_password') }
        personCreateButton() { $('input', value:'Create') }
    }

}
