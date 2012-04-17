package survey

class Enrollment implements Comparable{
    static belongsTo = [course:Course, person:Person]
    static constraints = {
        course()
        person(unique: 'course')
    }
    
    String toString() {
        person
    }
    
    @Override
    //This is only used for sorting names in a list.
    public int compareTo(Object o) {
	person.name <=> o.person.name
    }
}
