import survey.*
import survey.questions.*
import grails.util.GrailsUtil

class BootStrap {

    def init = { servletContext ->

        if (Person.count() == 0) { //GrailsUtil.environment != 'production') {
            def failOnError = [failOnError: true]
            def springString = 'Spring'
            def twentyTwelve = 2012
			def choiceList = ['Boy', 'Girl', 'No one', 'Other'] 
			def boutYerselfString = 'So, tell me \'bout yerself.'
			def animalTypeString = 'If you were an animal, what animal would you be?'


            // People -------------------------------------
            
            Person sid = new Person(name: 'Sid Anderson',
                                    password: 'shiboleet',
                                    email: 'sid@anderson.net',
                                    isAdmin: true).save(failOnError)

            Person nic = new Person(name: 'Nic McPhee',
                                    password: 'thomas',
                                    email: 'mcphee@morris.umn.edu',
                                    isAdmin: true).save(failOnError)
            Person kkLamberty = new Person(name: 'KK Lamberty',
                                           password: 'spencer',
                                           email: 'lamberty@morris.umn.edu',
                                           isAdmin: true).save(failOnError)
            Person sarah = new Person(name: 'Sarah Buchanan',
                                      password: 'paris',
                                      email: 'buchansb@morris.umn.edu',
                                      isAdmin: true).save(failOnError)
            Person joeB = new Person(name: 'Joseph Beaver',
                                     password: 'rcjh!',
                                     email: 'jbeaver@morris.umn.edu',
                                     isAdmin: true).save(failOnError)

            Course softwareDesign = new Course(abbreviation:'CSCI 3601', name:'Software Design',
                    term: springString,year: twentyTwelve, owner: nic).save(failOnError)
            Course introToComputing = new Course(abbreviation:'CSCI 1001', name:'Intro to the Computing World',
                    term: springString,year: 2013, owner: kkLamberty).save(failOnError)
            Course intermediateFrenchII = new Course(abbreviation:'FREN 2002', name: 'Intermediate French II',
                    term: springString, year: twentyTwelve, owner: sarah).save(failOnError)
            Course dataStructures = new Course(abbreviation:'CSCI 2101', name: 'Data Structures',
                    term: 'Fall', year: twentyTwelve, owner: joeB)
			dataStructures.save(failOnError)

            def defaultPassword = 'password'
            def buildPerson = { person -> new Person(name: person[0] + ' ' + person[1],
                password: defaultPassword, email: person[2]) }
            def enrollPerson = { person, course -> new Enrollment(person: person,
                course: course).save(failOnError) }

            def softwareDesignPeople = []
            [
                ['Phil', 'McGraw', 'phil@gmail.com'],
                ['Oprah', 'Winfrey', 'oprah@oprah.com'],
                ['Rachael', 'Ray', 'rr@cooking.com'],
                ['Whoopi', 'Goldberg', 'whoopi@view.com'],
                ['Regis', 'Philbin', 'i_hate_kathy@live.tv'],
                ['Chris', 'Aga', 'chris@aga.me'],
                ['Joseph', 'Einertson', 'joe@einertson.me'],
                ['Christopher', 'Thomas', 'chris@thomas.me'],
                ['Michael', 'Rislow', 'michael@rislow.me'],
                ['Kevin', 'Viratyosin', 'kevin@viratyosin.me'],
                ['Matt', 'Cotter', 'matt@cotter.me'],
                ['Steven', 'Jungst', 'steve@jungst.me'],
                ['Steve', 'Jobs', 'steve@apple.com'],
                ['Edgar', 'Poe', 'eap@poe.org'],
                ['Annabel', 'Leef', 'annabel@poe.org'],
                ['Phou', 'Lee', 'phou@lee.me'],
                ['Scott', 'Steffes', 'scott@steffes.me']

            ].each { person -> softwareDesignPeople.add(buildPerson(person)) }
            softwareDesignPeople.each { it.save(failOnError) }
            softwareDesignPeople.each { enrollPerson it, softwareDesign }
            enrollPerson joeB, softwareDesign

            def introToComputingPeople = []
            [
                ['James', 'Aronson', 'james@aronson.me'],
                ['Darcey', 'Aronsone', 'darcey@aronsone.me'],
                ['Bill', 'Gates', 'bill@microsoft.com'],
                ['George', 'Bush', 'george@bush.com'],
                ['Joevin', 'Einertyosin', 'joevin@einertyosin.org'],
                ['Reed', 'Wallace', 'reed@wallace.me'],
                ['Adrian', 'Schiller', 'imsocool@seriously.nah'],
                ['Denard', 'Span', 'span@twins.com'],
                ['Jamie', 'Carroll', 'carroll@twins.com'],
                ['Joe','Mauer','mauer@twins.com'],
                ['Justin','Morneau','morneau@twins.com'],
                ['Josh','Willingham','willingham@twins.com'],
                ['Ryan','Doumit','dournit@twins.com'],
                ['Danny','Valencia','valencia@twins.com'],
                ['Christie','Parmelee','parmelee@twins.com'],
                ['Alexi','Casilla','casilla@twins.com']

            ].each { person -> introToComputingPeople.add(buildPerson(person)) }
            introToComputingPeople.each { it.save(failOnError) }
            introToComputingPeople.each { enrollPerson it, introToComputing }
            
            def intermediateFrenchIIPeople = []
            [
                ['Tim', 'Snyder', 'tim@snyder.me'],
                ['Victor', 'Hugo', 'victor@hugo.org'],
                ['Arthur', 'Rimbaud', 'arthur@rimbaud.co.fr'],
                ['Jenny', 'Morris', 'jenny@morris.me'],
                ['Thore', 'Dosdal', 'thore@thore.me'],
                ['Carla', 'Bruni', 'carla@carlabruni.com'],
                ['M', 'François', 'fran@m.org']
            ].each { person -> intermediateFrenchIIPeople.add(buildPerson(person)) }
            intermediateFrenchIIPeople.each { it.save(failOnError) }
            intermediateFrenchIIPeople.each { enrollPerson it, intermediateFrenchII }

            // Projects -----------------------------------
            
            Project releaseOne = new Project(name: 'Release One', description: 'Students do cool things',
                    course: softwareDesign, dueDate: new Date().next()).save(failOnError)
            Team teamAwesome = new Team(name: 'Team Awesome', project: releaseOne).save(failOnError)
            Team teamHumble = new Team(name: 'Team Humble', project: releaseOne).save(failOnError)

            for (i in 0..3) {
                new Membership(team: teamAwesome, member: softwareDesignPeople[i]).save(failOnError)
            }
            for (i in 6..10) {
                new Membership(team: teamHumble, member: softwareDesignPeople[i]).save(failOnError)
            } // TODO: make these for loops w/o duplication

            def surveyReleaseOneQuestions = [
                new ShortTextQuestion(prompt:'What stories did you complete?'),
                new MultipleChoiceQuestion(prompt:'What grade would you give your group?',
                    choices:['A', 'B', 'C', 'D', 'F']),
                new LongTextQuestion(prompt:'Describe your group experience:')
            ]

            def scootsSurveyQuestions = [
                new LongTextQuestion(prompt: boutYerselfString),
                new CheckboxQuestion(prompt: 'Are you a boy or a girl?', choices: ['Boy', 'Girl', 'Other']),
                new LongTextQuestion(prompt: 'And how d\'you feel \'bout that?'),
                new ShortTextQuestion(prompt: animalTypeString),
                new MultipleChoiceQuestion(prompt: 'What\'s your favorite range in the visible light spectrum?',
                choices: ['380-450 (violet)', '450-475 (blue)', '476-495 (cyan)', '495-570 (green)',
                    '570-590 (yellow)', '590-620 (orange)', '620-750 (red)', '760+ (infrared)']),
                new ShortTextQuestion(prompt: 'What is your favorite vowel-less word?'),
                new LongTextQuestion(prompt: 'Tell me a story.'),
                new CheckboxQuestion(prompt: 'Do you love me?', choices: ['Yes', 'More than yes (wink, wink)'])
            ]

            Survey surveyReleaseOne = new Survey(title: 'Survey for Release One', dueDate: new Date().next(),
                    questions: surveyReleaseOneQuestions, project: releaseOne)
            Survey scootsSurvey = new Survey(title: 'Scoot\'s Finest Survey', dueDate: new Date().next(),
                    questions: scootsSurveyQuestions, project: releaseOne)

            surveyReleaseOne.save(failOnError)
            scootsSurvey.save(failOnError)
            
            Project releaseFourDemo = new Project(name: 'Release Four Demo',
                    description: 'Teams will present their projects to other students bribed by cookies.',
                    course: softwareDesign,
                    dueDate: new Date().next())
			releaseFourDemo.save(failOnError)
            
            Project examenOral = new Project(name: 'Un Examen Oral',
	   description:'Vous et un(e) partenaire préparerez une présantation où vous discuterez le sujet du chapitre.',
                    course: intermediateFrenchII,
                    dueDate: new Date().next()).save(failOnError)
            
            for (i in 1..3) {
                Team oralExamPair = new Team(name: 'Équipe ' + i, project: examenOral).save(failOnError)
                for ( j in (i-1)*2 ) {
                    new Membership(team: oralExamPair, member: intermediateFrenchIIPeople[j]).save(failOnError)
                    new Membership(team: oralExamPair, member: intermediateFrenchIIPeople[j+1]).save(failOnError)
                }
            }
            
            // Demo
            
            Course humanInteraction = new Course(abbreviation:'HINT 3113', name:'Human Interaction With Computers',
                    term: springString,year: twentyTwelve, owner: sid).save(failOnError)
            introToComputingPeople.each { enrollPerson it, humanInteraction }
            softwareDesignPeople.each { enrollPerson it, humanInteraction }
            intermediateFrenchIIPeople.each { enrollPerson it, humanInteraction }
            
            Project demo = new Project(name: 'Interaction Demonstration',
                                       description: 'Students learn how to interact with modified computers.',
                                       course: humanInteraction,
                                       dueDate: new Date().next()).save(failOnError)
                    
            def demoSurveyQuestions = [
                new LongTextQuestion(prompt: boutYerselfString),
                new MultipleChoiceQuestion(prompt: 'Look to your left. What do you see?',
                                           choices: choiceList),
                new MultipleChoiceQuestion(prompt: 'Look to your right. What do you see?',
                                           choices: choiceList),
                new LongTextQuestion(prompt: 'Tell me about the person on your left. (Do you like them?)'),
                new LongTextQuestion(prompt: 'Tell me about the person on your right. (Do you like them?)'),
                new ShortTextQuestion(prompt: animalTypeString),
                new ShortTextQuestion(prompt: 'How many cookies have you had?'),
                new LongTextQuestion(prompt: 'Tell me how those cookies made you feel.'),
                new CheckboxQuestion(prompt: 'Which of these fictional characters would like to have as friends?',
                                     choices: ['Cthulu', 'Kevin', 'Harry Potter',
										 	   'Bond: James Bond', 'Goku', 'The Smurfs', 'Superman']),
                new MultipleChoiceQuestion(prompt: 'On a scale of 0 to 4, how bored are you right now? Be honest.',
                                           choices: ['0','1','2','3','4'] )
            ]
            new Survey(title: 'Personal Survey', dueDate: new Date().next(),
                questions: demoSurveyQuestions, project: demo).save(failOnError)
        }
    }

    def destroy = {
    }
}
