package edu.uchicago.caor.quiz.model


import java.util.*


class Question(
    val english: String,
    val latin: String,
    val greek: String,
    val chinese: String,
    val allAnswers: MutableList<String> = mutableListOf()
) {
    private val random = Random()

    fun addAnswer(answer: String) {
        if (allAnswers.size == 0){
            allAnswers.add(answer)
        } else {
            val insertAt = random.nextInt(allAnswers.size)
            allAnswers.add(insertAt, answer)
        }
    }

    val questionText: String
        get() = "Which is $english?"

}