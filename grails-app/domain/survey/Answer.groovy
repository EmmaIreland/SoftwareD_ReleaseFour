package survey

class Answer {
    static belongsTo = [question: Question, person: Person, report: Report]
    
    static constraints = {
	report(nullable: true)
    }
}
