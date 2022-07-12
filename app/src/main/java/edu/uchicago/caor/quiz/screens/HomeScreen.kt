package edu.uchicago.caor.quiz.screens

import android.app.Activity
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
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
fun HomeScreen(navController: NavController, viewModel: QuizViewModel) {


    val activity = (LocalContext.current as? Activity)
    val localFocusManager = LocalFocusManager.current
    val playerName = viewModel.playerName


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
                .padding(horizontal = 16.dp, vertical = 24.dp),
        ) {
            Column {
                Box(modifier = Modifier.height(24.dp))

                Text(
                    text = "Welcome to Classics Quiz",
                    style = MaterialTheme.typography.h5
                )

                Box(modifier = Modifier.height(40.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Your Name",
                        modifier = Modifier.padding(vertical = 8.dp),
                        style = MaterialTheme.typography.body1
                    )
                    OutlinedTextField(
                        value = playerName.value,
                        onValueChange = viewModel::setPlayerName,
                        modifier = Modifier
                            .background(Color.Transparent)
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                localFocusManager.clearFocus()
                            }
                        ),
                        maxLines = 1
                    )
                }

                Box(modifier = Modifier.height(100.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()

                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.height(330.dp)) {
                            Button(
                                onClick = {
                                    viewModel.getQuestion()
                                    if (playerName.value.isNotBlank()) {
                                        viewModel.setPlayerName(playerName.value)
                                    }
                                    navController.navigate(Screen.QuestionScreen.route)
                                },
                                modifier = Modifier
                                    .weight(2f)
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                Text(
                                    text = "Latin",
                                    style = MaterialTheme.typography.button.copy(
                                        fontSize = 24.sp
                                    )
                                )
                            }

                            Box(modifier = Modifier.width(5.dp))

                            Button(
                                onClick = {
                                    viewModel.getQuestion()
                                    if (playerName.value.isNotBlank()) {
                                        viewModel.setPlayerName(playerName.value)
                                    }
                                    navController.navigate(Screen.QuestionScreen.route)
                                },
                                modifier = Modifier
                                    .weight(2f)
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                Text(
                                    text = "Greek",
                                    style = MaterialTheme.typography.button.copy(
                                        fontSize = 24.sp
                                    )
                                )
                            }

                            Box(modifier = Modifier.width(5.dp))

                            Button(
                                onClick = {
                                    viewModel.getQuestion()
                                    if (playerName.value.isNotBlank()) {
                                        viewModel.setPlayerName(playerName.value)
                                    }
                                    navController.navigate(Screen.QuestionScreen.route)
                                },
                                modifier = Modifier
                                    .weight(2f)
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                Text(
                                    text = "Mixed",
                                    style = MaterialTheme.typography.button.copy(
                                        fontSize = 24.sp
                                    )
                                )
                            }
                        }

                        Box(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                activity?.finish()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.Green)
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = RedColor)
                        ) {
                            Text(
                                text = "Exit", style = MaterialTheme.typography.button.copy(
                                    fontSize = 24.sp,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(quizViewModel: QuizViewModel = QuizViewModel(Application())) {
    HomeScreen(navController = rememberNavController(), viewModel = quizViewModel)
}