package edu.uchicago.caor.quiz.model


import java.util.*


class Question(
    val country: String,
    val capital: String,
    val region: String,
    val allAnswers: MutableList<String> = mutableListOf()
) {
    private val random = Random()

    fun addAnswer(answer: String) {
        if (allAnswers.size == 0){
            allAnswers.add(answer)
        } else {
            val insertAt = random.nextInt(allAnswers.size)
            allAnswers.add(insertAt, answer )
        }
    }

    val questionText: String
        get() = "What is the capital of $country?"

}