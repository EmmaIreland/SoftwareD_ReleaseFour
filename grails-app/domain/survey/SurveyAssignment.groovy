package survey

class SurveyAssignment implements Comparable<SurveyAssignment> {
    boolean completed = false
    static belongsTo = [survey: Survey, person:Person]
    
    static constraints = {
	survey(unique: 'person')
	person(unique: 'survey')
    }
    
    String toString() {
	person + " needs to take " + survey
    }
    
    public int compareTo(Object o) {
	person.name <=> o.person.name
    }
}
