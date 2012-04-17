package survey

class Course {
    String abbreviation
    String name
    String term
    int year
    SortedSet enrollments
    private static final int CURRENT_YEAR = Integer.parseInt(new Date().format('yyyy'))
    
    static hasMany = [enrollments:Enrollment, projects:Project]
    static belongsTo = [owner:Person]
    
    static constraints = {
        abbreviation blank: false
        name blank: false
        term(blank: false, inList: [ 'Fall', 'Spring', 'May', 'Summer I', 'Summer II' ] )
	year(blank: false, inList: [ CURRENT_YEAR, CURRENT_YEAR + 1 ] )
    }
    
    String toString() {
        name
    }
    
    boolean equals(Object other) {
        id == other.id
    }

    int hashCode() {
        id.hashCode()
    }
    
}
