package survey

class Report {
    Date dateTaken = new Date()
    static belongsTo = [person: Person, survey: Survey]
    static hasMany = [answers: Answer] 
    
    static constraints = {
	answers(nullable: true)
    }
    
    def isOverdue() {
        return dateTaken > survey.dueDate
    }
}
