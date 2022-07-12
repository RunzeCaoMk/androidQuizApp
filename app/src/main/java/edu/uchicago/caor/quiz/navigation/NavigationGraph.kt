package edu.uchicago.caor.quiz.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import edu.uchicago.caor.quiz.viewmodels.QuizViewModel
import androidx.navigation.compose.NavHost

@Composable
fun NavigationGraph(navController: NavHostController) {
    val viewModel: QuizViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController, viewModel)
        }
        composable(route = Screen.QuestionScreen.route) {
            QuestionScreen(navController, viewModel)
        }
        composable(route = Screen.ResultScreen.route) {
            ResultScreen(navController, viewModel)
        }
    }
}