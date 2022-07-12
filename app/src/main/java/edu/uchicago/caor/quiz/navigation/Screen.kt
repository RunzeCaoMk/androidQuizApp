package edu.uchicago.caor.quiz.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen(route = "home_screen")
    object QuestionScreen : Screen(route = "question_screen")
    object ResultScreen : Screen(route = "result_screen")
}