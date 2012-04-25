package survey

class Report {
    static belongsTo = [person: Person, survey: Survey]
    static hasMany = [answers: Answer] 
    
    static constraints = {
	answers(nullable: true)
    }
}
