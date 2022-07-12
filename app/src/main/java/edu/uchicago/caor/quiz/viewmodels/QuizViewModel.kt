package edu.uchicago.caor.quiz.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel

@HiltViewModel
class QuizViewModel @Inject constructor(private val application: Application) : ViewModel{
}