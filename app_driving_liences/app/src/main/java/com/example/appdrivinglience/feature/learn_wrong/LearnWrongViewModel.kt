package com.example.appdrivinglience.feature.learn_wrong

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdrivinglience.database.dao.QuestionDao
import com.example.appdrivinglience.database.dao.QuestionWrongDao
import com.example.appdrivinglience.database.model.Question
import com.example.appdrivinglience.database.model.QuestionWrongModel
import com.example.appdrivinglience.feature.trick_screen.TrickModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearnWrongViewModel @Inject constructor(
    private val questionWrongDao: QuestionWrongDao,
    private val questionDao: QuestionDao
) : ViewModel() {
    private val _questionListState = mutableStateOf<LearnWrongState>(LearnWrongState.Loading)
    val questionListState: State<LearnWrongState> = _questionListState

    fun getAllListQuestionWrong(){
        _questionListState.value = LearnWrongState.Loading
        viewModelScope.launch {

            val listQuestionWrong = questionWrongDao.getAllQuestionWrong()
            if (listQuestionWrong.isNotEmpty()){
                val listQuestions = mutableListOf<Question>()
                listQuestionWrong.forEach {
                    listQuestions.addAll(questionDao.getListQuestionById(it.idQuestion))
                }
                _questionListState.value = LearnWrongState.Success(listQuestions)
            }else{
                _questionListState.value = LearnWrongState.Success(mutableListOf())
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

sealed class LearnWrongState {
    data object Loading : LearnWrongState()
    data class Success(val dataQuestionList : List<Question>) : LearnWrongState()
    data class Error(val error : String?) : LearnWrongState()
}