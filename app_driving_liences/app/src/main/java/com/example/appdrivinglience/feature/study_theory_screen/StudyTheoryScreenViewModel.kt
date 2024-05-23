package com.example.appdrivinglience.feature.study_theory_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdrivinglience.database.dao.QuestionDao
import com.example.appdrivinglience.database.dao.QuestionWrongDao
import com.example.appdrivinglience.database.model.Question
import com.example.appdrivinglience.database.model.QuestionWrongModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class StudyTheoryScreenViewModel @Inject constructor(
    private val questionDao: QuestionDao,
    private val questionWrongDao: QuestionWrongDao
) : ViewModel() {
    private val _questionState = mutableStateOf<QuestionState>(QuestionState.Loading)
    val questionState : State<QuestionState> = _questionState
    fun getQuestionByIdCategory(idCategory: Long){
        _questionState.value = QuestionState.Loading
        viewModelScope.launch {
            runCatching {
                if (idCategory == 0L){
                        val listAllQuestion = questionDao.getAllQuestion().mapIndexed { index, question ->
                          question.question = "Câu ${index + 1}:"+question.question.substring(question.question.indexOfFirst { it == ':' }+1)
                          question
                        }
                    _questionState.value = QuestionState.Success(listAllQuestion)
                }else {
                    _questionState.value =
                        QuestionState.Success(questionDao.getListQuestionByIdCategory(idCategory))
                }
            }.onFailure {
                _questionState.value = QuestionState.Error(it.message)
            }
        }
    }
    fun saveDataQuestion(question: QuestionWrongModel){
        viewModelScope.launch {
            questionWrongDao.insertQuestionWrong(question)
        }
    }
    fun deleteDataQuestion(id: Long){
        viewModelScope.launch {
            questionWrongDao.deleteQuestionWrongById(id)
        }
    }
}

sealed class QuestionState {

    data object Loading : QuestionState()
    data class Success(val listQuestion : List<Question>) : QuestionState()
    data class Error(val error: String?) : QuestionState()
}