import survey.*
import survey.questions.*
import grails.util.GrailsUtil

class BootStrap {

    def init = { servletContext ->

        if (Person.count() == 0) { //GrailsUtil.environment != 'production') {
            def failOnError = [failOnError: true]
            def fallString = 'Fall'
            def springString = 'Spring'
            def twentyTwelve = 2012
            def twentyThirteen = 2013

            def defaultPassword = 'password'
            def buildPerson = { person -> new Person(name: person[0] + ' ' + person[1],
                password: defaultPassword, email: person[2]) }
            def enrollPerson = { person, course -> new Enrollment(person: person,
                course: course).save(failOnError) }
            def assignTeams = { teamNames, project ->
                teamNames.each { name ->
                     new Team(name: name, project: project).save(failOnError)
                }
                def people = Enrollment.findAllByCourse(project.course)*.person // can't do project.course.enrollments*.person ?
                def teams = Team.findAllByProject(project) // can't do project.teams ?
                def teamCount = teams.size()
                people.eachWithIndex { person, index ->
                    new Membership(team: teams[index % teamCount], member: person).save(failOnError)
                }
            }
            
            Person sid = new Person(name: 'Sid Anderson',
                                    password: 'shiboleet',
                                    email: 'sid@anderson.net',
                                    isAdmin: true).save(failOnError)
            Person ben = new Person(name: 'Ben Esterling',
                                    password: 'cornhusk',
                                    email: 'besterling@cornfarms.com',
                                    isAdmin: true).save(failOnError)
            Person wally = new Person(name: 'Wally Odlawsen',
                                      password: 'waldo',
                                      email: 'wally@where.com',
                                      isAdmin: true).save(failOnError)
            Person nic = new Person(name: 'Nic Pherson',
                                    password: 'thomas',
                                    email: 'pherson@utaustin.edu',
                                    isAdmin: true).save(failOnError)
            Person quincy = new Person(name: 'Quincy Adams',
                                       password: 'federalists',
                                       email: 'qadams@whitehouse.gov',
                                       isAdmin: true).save(failOnError)
                                       
            Course dataStructures = new Course(abbreviation:'CSCI 2213', name: 'Data Structures and Java',
                term: springString, year: twentyThirteen, owner: sid).save(failOnError)
            Course honorsProgramming = new Course(abbreviation:'CSCI 1401', name: 'Honors Computer Programming',
                term: fallString, year: twentyThirteen, owner: sid).save(failOnError)
            Course seniorSeminar = new Course(abbreviation:'CSCI 4001', name: 'Senior Seminar',
                term: springString, year: twentyThirteen, owner: sid).save(failOnError)
            Course introAgriculture = new Course(abbreviation:'AGRI 1101', name: 'Introduction to Agriculture',
                term: fallString, year: twentyTwelve, owner: ben).save(failOnError)
            Course surveyOfBotany = new Course(abbreviation:'BIOL 2104', name: 'Survey of Botany',
                term: springString, year: twentyTwelve, owner: ben).save(failOnError)
            Course mapsAndGraphs = new Course(abbreviation:'MATH 2951', name: 'Applications of Maps and Graphs',
                term: springString, year: twentyThirteen, owner: wally).save(failOnError)
            Course problemSolving = new Course(abbreviation:'MATH 1913', name: 'Problem Solving',
                term: fallString, year: twentyTwelve, owner: wally).save(failOnError)
            Course softwareDesign = new Course(abbreviation:'CSCI 3602', name: 'Software Development and Design',
                term: springString, year: twentyTwelve, owner: nic).save(failOnError)
            Course artificialIntelligence = new Course(abbreviation:'CSCI 2215', name: 'Artificial Intelligence',
                term: fallString, year: twentyTwelve, owner: nic).save(failOnError)
            Course americanHistory = new Course(abbreviation:'HIST 1311', name: 'Early American History',
                term: fallString, year: twentyThirteen, owner: quincy).save(failOnError)

            def people = []
            [
                ['Phil', 'McGraw', 'phil@gmail.com'],
                ['Oprah', 'Winfrey', 'oprah@oprah.com'],
                ['Rachael', 'Ray', 'rr@cooking.com'],
                ['Roselia', 'Barayuga', 'roselia@barayuga.me'],
                ['Whoopi', 'Goldberg', 'whoopi@view.com'],
                ['Regis', 'Philbin', 'i_hate_kathy@live.tv'],
                ['Polly', 'Pochet', 'polly@pochet.me'],
                ['Stanley', 'Scalise', 'stanley@scalise.it'],
                ['Joevin', 'Einertyosin', 'joevin@einertyosin.me'],
                ['Richard', 'Quinnette', 'dick@quinnette.me'],
                ['Steve', 'Jobs', 'steve@apple.com'],
                ['Cristopher', 'Mycroft', 'cris@mycroft.me'],
                ['Kendall', 'Dahnke', 'ken@dahnke.me'],
                ['Raymond', 'Glenn', 'ray@glenn.me'],
                ['Hal', 'Morrison', 'hal@morrison.me'],
                ['Edgar', 'Poe', 'eap@poe.org'],
                ['Barry', 'Coll', 'barry@coll.me'],
                ['Annabel', 'Lee', 'annabel@poe.org'],
                ['Theory', 'Ven', 'raven@poe.org'],
                ['Newton', 'Mycroft', 'newton@mycroft.me'],
                ['Ash', 'Ketchum', 'ash@kan.to'],
                ['Carlos', 'Parham', 'carlos@parham.me'],
                ['Lakeesha', 'Settler', 'lakeesha@settler.me'],
                ['Bilbo', 'Baggins', 'baggins@shire.gov'],
                ['Ronald', 'Renfrow', 'ron@renfrow.me'],
                ['Trey', 'Clifford', 'trey@clifford.me'],
                ['E', 'Jabberwocky', 'jabberwocky@carol.org'],
                ['Ollie', 'Pearson', 'ollie@pearson.me'],
                ['Newt', 'Pearson', 'newt@pearson.me'],
                ['Quinton', 'Maedche', 'quinton@maedche.me'],
                ['Kayla', 'Rouses', 'kayla@rouses.me'],
                ['Anneliese', 'Lummis', 'anneliese@lummis.me'],
                ['Barack', 'Obama', 'obama@whitehouse.gov'],
                ['Ernst', 'Leitz', 'eleitz@elcan.ca'],
                ['Sid', 'Nicoson', 'sid@nicoson.me'],
                ['Lara', 'Segal', 'lara@segal.me'],
                ['Marie', 'Rimbaud', 'marie@rimbaud.org'],
                ['Vivian', 'Velvia', 'vivian@fuji.com'],
                ['Edwin', 'Land', 'eland@instant.org'],
                ['Henri', 'Cartier-Bresson', 'henri@cartier-bresson.org'],
                ['Pura', 'Cutten', 'pura@cutten.me'],
                ['Lucille', 'Ricardo', 'lucy@ilovelucy.tv'],
                ['Petra', 'Provia', 'provia@fuji.com'],
                ['Gordon', 'Freeman', 'gordon@blackmesa.com'],
                ['Ji', 'Saadeh', 'ji@saadeh.me'],
                ['Annie', 'Astia', 'astia@fuji.com'],
                ['P', 'Sherman', 'wallabyway@nemo.org'],
                ['E', 'Themo', 'ethemo@sstreet.tv'],
                ['John', 'Shepard', 'john@n7.gov'],
                ['Carly', 'Chu', 'carly@chu.me'],
                ['Elton', 'Grella', 'elton@grella.me'],
                ['Billy', 'Soares', 'billy@soares.me'],
                ['Paul', 'Gentil', 'paul@gentil.me']

            ].each { person -> people.add(buildPerson(person)) }
            people.each { it.save(failOnError) }
            
            people[0..15].each { enrollPerson it, dataStructures }
            people[3..11].each { enrollPerson it, honorsProgramming }
            people[17..21].each { enrollPerson it, seniorSeminar }
            people[23..26].each { enrollPerson it, introAgriculture }
            people[27..39].each { enrollPerson it, surveyOfBotany }
            people[22..36].each { enrollPerson it, mapsAndGraphs }
            people[29..44].each { enrollPerson it, problemSolving }
            people[48..-1].each { enrollPerson it, softwareDesign }
            people[2..20].each { enrollPerson it, artificialIntelligence }
            people[40..50].each { enrollPerson it, americanHistory }
            
            Project javaCollections = new Project(name: 'Java Collections',
                                                  description: 'Students learn how to use many data structures available in the Java API.',
                                                  course: dataStructures,
                                                  dueDate: new Date().next()).save(failOnError)
            assignTeams( [ 'Team List', 'Team Map', 'Team Tree', 'Team Array' ], javaCollections )
            
            def javaCollectionsQuestions = [
                new ShortTextQuestion(prompt: 'Which data structure did you use the most?'),
                new LongTextQuestion(prompt: 'Tell me about your experience with the Java documentation.'),
                new LongTextQuestion(prompt: 'How could this lab have gone better?'),
                new MultipleChoiceQuestion(prompt: 'How did you like your group members?',
                                           choices: ['I liked my group.', 'Neutral', 'I disliked my group.'])
            ]
            
            new Survey(title: 'Java Collections Review',
                       dueDate: new Date().next(),
                       questions: javaCollectionsQuestions,
                       project: javaCollections).save(failOnError)
        }
    }

    def destroy = {
    }
}
