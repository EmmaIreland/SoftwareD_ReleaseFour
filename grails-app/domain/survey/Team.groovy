package survey

class Team {
    String name
    String comments
    SortedSet memberships
    static hasMany = [memberships: Membership]
    static belongsTo = [project: Project]
    static final NULLABLE = [nullable: true]

    static constraints = {
        name(nullable: true, blank: false)      
        project(NULLABLE)
	comments(NULLABLE)
        memberships()
    }
    
    String toString() {
      name
    }
}