package pages

import geb.Page

class HomePage extends Page {
    static url = ""
    
    static at = {
        title.endsWith("TwentyFourEyes")
    }
    
    static content = { 
		coursesButton(to: CourseListPage) { $("a", text: "Courses") }
		projectsButton() { $("a", text: "Projects") }
		peopleButton() { $("a", text: "People") }
		surveysButton() { $("a", text: "Surveys")}
                logoutButton() { $("a", text: "Logout")}
    }
}