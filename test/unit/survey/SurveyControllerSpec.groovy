package survey

import grails.plugin.spock.*
import survey.answers.*
import survey.questions.*

class SurveyControllerSpec extends ControllerSpec {

    Person admin = new Person(name:'admin', email:'admin@admin.com', password:'admin')
    Person bob = new Person(name: 'bob', email: 'bob@bob.bob')
    Person steve = new Person(name: 'steve', email: 'steve@steve.com')
    Course testCourse = new Course(name:'test course', abbreviation:'test', term:'Fall', year:2012, owner:admin)
    Enrollment bobEnroll = new Enrollment(person: bob, course: testCourse)
    Project project = new Project(name: 'Lab 1', course: testCourse)
    Team testTeam = new Team(name:'test team', project: project, memberships:bobInTeam)
    Membership bobInTeam = new Membership(team:testTeam, member: bob)
    Survey testSurvey = new Survey(title: 'Test', project: project)
    Question shortText = new ShortTextQuestion()
    Question longText = new LongTextQuestion()
    Question multipleChoice = new MultipleChoiceQuestion()
    Question checkBox = new CheckboxQuestion()


    def setup(){
        mockForConstraintsTests(Survey)
        mockDomain(Person, [admin, bob, steve])
        mockDomain(Course, [testCourse])
        mockDomain(Enrollment, [bobEnroll])
        mockDomain(Project, [project])
        mockDomain(Team, [testTeam])
        mockDomain(Survey, [testSurvey])
        mockDomain(Question, [
            shortText,
            longText,
            multipleChoice,
            checkBox
        ])
    }
    def 'Test Submit Survey'() {
        Survey newSurvey = new Survey(title: 'Test', project: project, questions: [shortText])
        controller.metaClass.message = {''}
        
        when:
        controller.params.id = 1
        controller.params.personid = 1
        controller.submit()
        then:
        controller.redirectArgs != null

        when:
        controller.params.id = null
        controller.params.personid = null
        controller.submit()

        then:
        controller.redirectArgs.action == 'list'
    }    
    def 'Test createAnswer'() {
        expect:
        mockDomain(Answer, [])
        Answer.list().size() == 0
        controller.createAnswer(longText, bob, 'Stuff')
        
        Answer.list().size() == 1
    }
}
