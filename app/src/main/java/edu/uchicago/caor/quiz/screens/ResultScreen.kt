package edu.uchicago.caor.quiz.screens


import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.uchicago.caor.quiz.navigation.Screen
import edu.uchicago.caor.quiz.ui.theme.RedColor
import edu.uchicago.caor.quiz.viewmodels.QuizViewModel

@Composable
fun ResultScreen(navController: NavController, viewModel: QuizViewModel) {
    val correctSubmissions = viewModel.correctSubmissions.value
    val incorrectSubmissions = viewModel.incorrectSubmissions.value
    val scorePercent =
        100 * (correctSubmissions.toDouble() / (correctSubmissions + incorrectSubmissions))
    val playerName = viewModel.playerName.value

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Classics Quiz",
                    textAlign = TextAlign.Start,
                )
            },
        )
    }) {

        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(7f)
                ) {
                    Text(
                        text = "${playerName}, your results are:",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 40.dp),
                        style = MaterialTheme.typography.h6
                    )
                    Divider()
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.DarkGray)
                            ) {
                                Text(
                                    text = "Correct: ${correctSubmissions}",
                                    modifier = Modifier.padding(
                                        vertical = 18.dp,
                                        horizontal = 24.dp
                                    ),
                                    style = MaterialTheme.typography.h5.copy(
                                        color = Color.Green
                                    )
                                )
                            }
                        }

                        Box(modifier = Modifier.height(24.dp))

                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.DarkGray)
                            ) {
                                Text(
                                    text = "Incorrect: ${incorrectSubmissions}",
                                    modifier = Modifier.padding(
                                        vertical = 18.dp,
                                        horizontal = 24.dp
                                    ),
                                    style = MaterialTheme.typography.h5.copy(
                                        color = RedColor
                                    )
                                )
                            }
                        }

                        Box(modifier = Modifier.height(24.dp))

                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.DarkGray)
                            ) {
                                Text(
                                    text = "Score: ${scorePercent.toInt()}%",
                                    modifier = Modifier.padding(
                                        vertical = 18.dp,
                                        horizontal = 24.dp
                                    ),
                                    style = MaterialTheme.typography.h5.copy(
                                        color = Color.White
                                    )
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(12.dp),
                ) {
                    Button(
                        onClick = {
                            viewModel.anotherQuiz()
                            viewModel.getQuestion()
                            navController.popBackStack(Screen.ResultScreen.route, true)
                            navController.navigate(Screen.QuestionScreen.route)
                        },
                        modifier = Modifier
                            .weight(3f)
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = "Another Quiz", style = MaterialTheme.typography.button.copy(
                                fontSize = 16.sp
                            )
                        )
                    }

                    Box(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            viewModel.reset()
                            navController.popBackStack(Screen.ResultScreen.route, true)
                            navController.popBackStack(Screen.QuestionScreen.route, true)
//                            // TODO: navigate to Home screen
//                            navController.navigate(Screen.QuestionScreen.route)
                        },
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = RedColor)
                    ) {
                        Text(
                            text = "Reset", style = MaterialTheme.typography.button.copy(
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        )
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview(quizViewModel: QuizViewModel = QuizViewModel(Application())) {
    ResultScreen(navController = rememberNavController(), viewModel = quizViewModel)
}