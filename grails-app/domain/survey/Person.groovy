package survey

class Person {
    String name
    String email
    String password
    Boolean isAdmin = false
    static hasMany = [ownedCourses:Course, enrollments:Enrollment, reports: Report, \
        memberships: Membership, answers: Answer, surveyAssignments: SurveyAssignment]
    
    def authenticationService
    
    static constraints = {
        name(blank: false)
        email(email: true, blank:false, unique: true)
	password(blank: false, size: 5..100)
    }
    
    String toString() {
        name
    }
    
    def beforeInsert() {
        if (this.password) {
            this.password = authenticationService.hashPassword(this.password)
        }
    }
    

}
