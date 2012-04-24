package survey

class Survey {
    String title
    Date dueDate
    List questions
    static hasMany = [questions: Question, reports: Report]
    static belongsTo = [project: Project]
    
    static constraints = {
	title(blank: false)
	dueDate(validator: { dueDate -> dueDate.after(new Date().previous()) } )
	questions(nullable: true)
	project()
    }
    
    String toString() {
	title
    }
    
    def getTeamsForAssignment() {
        // Grab all current Teams plus the fake Team containing those not assigned to a Team
        project.teams.sort{it.name}.plus(constructFakeTeam())
    }
    
    private constructFakeTeam() {
        // Find all Enrollments for this Course and collect the Persons associated with them
        def courseStudents = Enrollment.findAllByCourse(project.course)*.person
        
        // Remove all Persons from the list who are already in a group
        courseStudents.removeAll(project.teams*.memberships.flatten()*.member)
        
        // Return a fake Team which will allow the views to not break
        [name: 'Students Not Assigned to a Team', memberships: constructFakeMemberships(courseStudents)] as Team
    }
    
    private constructFakeMemberships(unassignedStudents) {
        // Make each Person a fake Membership to allow views to not break
        unassignedStudents.collect { [member: it] as Membership }
    }
}
