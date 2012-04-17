package survey

class SurveyAssignment {
    static belongsTo = [survey: Survey, person:Person]
    
    static constraints = {
	survey()
	person()
    }
    
    String toString() {
	person + " needs to take " + survey
    }
    
    public int compareTo(Object o) {
	person.name.compareTo(o.person.name)
    }
}
