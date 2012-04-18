package survey.questions

import survey.*

class LongTextQuestion extends Question implements QuestionInterface{
    final Object templateName = 'Long'
    static hasMany = [responses: Answer]
    
    static constraints = {
    }
    
    Question copyQuestion() {
        def newQuestion = new LongTextQuestion(prompt: prompt).save(failOnError: true)
        newQuestion
    }
    
    String toString() {
        prompt
    }
}
