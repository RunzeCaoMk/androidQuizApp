package edu.uchicago.caor.quiz.viewmodels


import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uchicago.caor.quiz.R
import edu.uchicago.caor.quiz.model.Question
import edu.uchicago.caor.quiz.util.Constants.CHN_INDEX
import edu.uchicago.caor.quiz.util.Constants.LAT_INDEX
import edu.uchicago.caor.quiz.util.Constants.ENG_INDEX
import edu.uchicago.caor.quiz.util.Constants.PIPE
import edu.uchicago.caor.quiz.util.Constants.GRK_INDEX
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
//we inject a reference to the application object to gain access to the resources in strings.xml
class QuizViewModel @Inject constructor(private val application: Application) : ViewModel() {

    //this value used on the HomeScreen
    private var _playerName = mutableStateOf("Mark")
    val playerName: State<String> = _playerName
    private var _langOption = mutableStateOf("Latin")
    val langOption: State<String> = _langOption


    //these values used on the QuestionScreen
    //the following is used for preview-only
    private var previewAnswers = mutableListOf("Neró", "Etos", "Skýlos", "Kefáli", "Kýklos")
    private var _question = mutableStateOf<Question>(Question("Horse", "Equus", "Hippo", "马", previewAnswers))
    val question: State<Question> = _question

    private var _questionNumber = mutableStateOf<Int>(1)
    val questionNumber: State<Int> = _questionNumber

    private var _selectedOption = mutableStateOf<String>("Neró")
    val selectedOption: State<String> = _selectedOption

    //these values used on the ResultScreen
    private var _correctSubmissions = mutableStateOf<Int>(92);
    val correctSubmissions: State<Int> = _correctSubmissions

    private var _incorrectSubmissions = mutableStateOf<Int>(8);
    val incorrectSubmissions: State<Int> = _incorrectSubmissions


    init {
        //clear out the default values above which are used in Preview mode
        reset()
        clearSelectedOption()
        getQuestion()
    }


    //////////////////////////////////
    //methods for HomeScreen
    //////////////////////////////////
    fun setPlayerName(name: String) {
        _playerName.value = name
    }
    fun setLangOption(opt: String) {
        _langOption.value = opt
    }

    //////////////////////////////////
    //methods for QuestionScreen
    //////////////////////////////////
    //this method will fetch a random item from resources array such as <item>Horse|Equus|Hippo</item>
    //and then split and return it as List<String>
    private suspend fun getPipedQA() : List<String> {

        //arrayDeferred is the future value returned by .async
        val arrayDeferred = CoroutineScope(Dispatchers.IO).async {
            application.resources.getStringArray(R.array.classic_words)
        }
        //calling .await() forces execution to wait until the value gets returned
        val array = arrayDeferred.await()
        val index: Int = Random.nextInt(array.size)
        return array[index].split(PIPE)

    }

    fun getQuestion() {
        //in order to update the UI, we must be on the UI aka Main thread in Android
        viewModelScope.launch(Dispatchers.Main) {
            //gets a random english word from the array in resources
            val correctAnswer: List<String> = getPipedQA()
            //convert it into a new question object
            val question =
                Question(
                    correctAnswer[ENG_INDEX],
                    correctAnswer[LAT_INDEX],
                    correctAnswer[GRK_INDEX],
                    correctAnswer[CHN_INDEX]
                )

            while (question.allAnswers.size < 5) {
                var potentialWrongAnswer = getPipedQA()
                if (_langOption.value == "Latin") { // Latin Mode
                    while (
                    //the word of potentialWrongAnswer is the same as correctAnswer then skip
                        potentialWrongAnswer[LAT_INDEX] == correctAnswer[LAT_INDEX] ||
                        //the wrong answers already contain the potentialWrongAnswer, skip
                        question.allAnswers.contains(potentialWrongAnswer[LAT_INDEX])
                    ) {
                        //go fetch another one
                        potentialWrongAnswer = getPipedQA()
                    }
                    //add the capital of the validated potentialWrongAnswer to the wrong answers of the question
                    question.addAnswer(potentialWrongAnswer[LAT_INDEX])
                } else if (_langOption.value == "Greek") { // Greek Mode
                    while (
                        potentialWrongAnswer[GRK_INDEX] == correctAnswer[GRK_INDEX] ||
                        question.allAnswers.contains(potentialWrongAnswer[GRK_INDEX])
                    ) {
                        potentialWrongAnswer = getPipedQA()
                    }
                    question.addAnswer(potentialWrongAnswer[GRK_INDEX])
                } else if (_langOption.value == "Chinese") { // Chinese Mode
                    while (
                        potentialWrongAnswer[CHN_INDEX] == correctAnswer[CHN_INDEX] ||
                        question.allAnswers.contains(potentialWrongAnswer[CHN_INDEX])
                    ) {
                        potentialWrongAnswer = getPipedQA()
                    }
                    question.addAnswer(potentialWrongAnswer[CHN_INDEX])
                } else { // Mixed Mode
                    while (
                        potentialWrongAnswer[LAT_INDEX] == correctAnswer[LAT_INDEX] ||
                        potentialWrongAnswer[GRK_INDEX] == correctAnswer[GRK_INDEX] ||
                        potentialWrongAnswer[CHN_INDEX] == correctAnswer[CHN_INDEX] ||
                        question.allAnswers.contains(potentialWrongAnswer[LAT_INDEX]) ||
                        question.allAnswers.contains(potentialWrongAnswer[GRK_INDEX]) ||
                        question.allAnswers.contains(potentialWrongAnswer[CHN_INDEX])
                    ) {
                        potentialWrongAnswer = getPipedQA()
                    }
                    val randomIndex : Int = Random.nextInt(1, 3)
                    question.addAnswer(potentialWrongAnswer[randomIndex])
                }
            }
            //add the correct answer
            question.addAnswer(question.latin)
            _question.value = question
        }
    }

    fun submitAnswer(question: Question) {
        //to update the mutable-state, we first get the value from the state, increment it,
        val nextNumber: Int = questionNumber.value + 1
        //and then set the intermediate variable to the mutable-state
        _questionNumber.value = nextNumber

        if (_langOption.value == "Latin") { // Latin Mode
            //if the user selected the correct answer
            if (question.latin == selectedOption.value) {
                incrementCorrect()
            } else {
                incrementIncorrect()
            }
        } else if (_langOption.value == "Greek") { // Greek Mode
            //if the user selected the correct answer
            if (question.greek == selectedOption.value) {
                incrementCorrect()
            } else {
                incrementIncorrect()
            }
        } else if (_langOption.value == "Chinese") { // Chinese Mode
            //if the user selected the correct answer
            if (question.chinese == selectedOption.value) {
                incrementCorrect()
            } else {
                incrementIncorrect()
            }
        } else { // Mixed Mode
            //if the user selected the correct answer
            if (
                question.latin == selectedOption.value ||
                question.greek == selectedOption.value ||
                question.chinese == selectedOption.value
            ) {
                incrementCorrect()
            } else {
                incrementIncorrect()
            }
        }

        //queue up another valid question
        getQuestion()
        //clear out the selected value
        clearSelectedOption()
    }

    private fun incrementCorrect() {
        val correctSubmitted = correctSubmissions.value + 1
        _correctSubmissions.value = correctSubmitted
    }

    private fun incrementIncorrect() {
        val incorrectSubmitted = incorrectSubmissions.value + 1
        _incorrectSubmissions.value = incorrectSubmitted
    }

    fun selectOption(option: String) {
        _selectedOption.value = option
    }

    private fun clearSelectedOption() {
        _selectedOption.value = ""
    }

    //////////////////////////////////
    //methods for ResultScreen
    //////////////////////////////////
    fun anotherQuiz() {
        _correctSubmissions.value = 0
        _incorrectSubmissions.value = 0
        _questionNumber.value = 1
    }

    fun reset() {
        anotherQuiz()
        _playerName.value = ""
        _langOption.value = ""
    }

}

