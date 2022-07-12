package edu.uchicago.caor.quiz.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
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
import edu.uchicago.caor.quiz.model.Question
import edu.uchicago.caor.quiz.navigation.Screen
import edu.uchicago.caor.quiz.ui.theme.RedColor
import edu.uchicago.caor.quiz.viewmodels.QuizViewModel

@Composable
fun QuestionScreen(navController: NavController, viewModel: QuizViewModel) {

    val selectedOption: String = viewModel.selectedOption.value
    val question: Question = viewModel.question.value
    val questionNumber: Int = viewModel.questionNumber.value
    val answers: List<String> = viewModel.question.value.allAnswers

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
                        text = "Question $questionNumber :",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                        style = MaterialTheme.typography.subtitle2
                    )
                    Divider()
                    Text(
                        text = question.questionText,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                        style = MaterialTheme.typography.h6
                    )
                    answers.forEach { option ->
                        Box(modifier = Modifier
                            .padding(10.dp)
                            .selectable(
                                selected = (option == selectedOption),
                                onClick = { viewModel.selectOption(option = option) }
                            )) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)

                            ) {
                                RadioButton(selected = selectedOption == option, onClick = {
                                    viewModel.selectOption(option = option)
                                })
                                Text(
                                    text = option,
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    style = MaterialTheme.typography.subtitle1
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
                            viewModel.submitAnswer(question = question)
                        },
                        modifier = Modifier
                            .weight(3f)
                            .fillMaxHeight(),
                        enabled = selectedOption.isNotBlank()
                    ) {
                        Text(
                            text = "Submit", style = MaterialTheme.typography.button.copy(
                                fontSize = 16.sp
                            )
                        )
                    }
                    Box(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            navController.popBackStack(Screen.QuestionScreen.route, true)
                            navController.navigate(Screen.ResultScreen.route)
                        },
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = RedColor)

                    ) {
                        Text(
                            text = "Quit", style = MaterialTheme.typography.button.copy(
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
fun QuestionScreenPreview(quizViewModel: QuizViewModel = QuizViewModel(Application())) {
    QuestionScreen(navController = rememberNavController(), viewModel = quizViewModel)
}
