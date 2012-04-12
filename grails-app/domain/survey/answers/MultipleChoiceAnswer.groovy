package survey.answers

import survey.*

class MultipleChoiceAnswer extends Answer {
    int responseIndex

    static constraints = {
    }
    
    String toString() {
        question.choices[responseIndex]
    }
}
