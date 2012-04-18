package survey.questions

import survey.*
import survey.answers.*

class ShortTextQuestion extends Question implements QuestionInterface {
	final Object templateName = 'Short'
    static hasMany = [responses: Answer]

    static constraints = {
    }

    Question copyQuestion() {
        def newQuestion = new ShortTextQuestion(prompt: prompt).save(failOnError: true)
        newQuestion
    }
    
    String toString() {
        prompt
    }

}
